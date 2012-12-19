/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.test;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.process.ClassPathUtil;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.process.ProcessExecutor;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import java.lang.reflect.InvocationTargetException;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.manipulation.Sorter;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * @author Shuyang Zhou
 */
public class NewJVMJUnitTestRunner extends BlockJUnit4ClassRunner {

	public NewJVMJUnitTestRunner(Class<?> clazz) throws InitializationError {
		super(clazz);

		_classPath = ClassPathUtil.getJVMClassPath(false);

		sort(new Sorter(new DescriptionComparator()));
	}

	protected List<String> createArguments(FrameworkMethod frameworkMethod) {
		List<String> arguments = new ArrayList<String>();

		boolean junitDebug = Boolean.getBoolean("junit.debug");

		if (junitDebug) {
			arguments.add(_JPDA_OPTIONS);
		}

		arguments.add("-Djava.net.preferIPv4Stack=true");

		String fileName = System.getProperty(
			"net.sourceforge.cobertura.datafile");

		if (fileName != null) {
			arguments.add("-Dnet.sourceforge.cobertura.datafile=" + fileName);
		}

		return arguments;
	}

	protected ServerSocket createServerSocket() {
		int port = _START_SERVER_PORT;

		while (true) {
			try {
				ServerSocket serverSocket = new ServerSocket();

				serverSocket.setReuseAddress(true);

				serverSocket.bind(
					new InetSocketAddress(InetAddress.getLocalHost(), port));

				return serverSocket;
			}
			catch (IOException ioe) {
				port++;
			}
		}
	}

	@Override
	protected Statement methodBlock(FrameworkMethod frameworkMethod) {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		PortalClassLoaderUtil.setClassLoader(contextClassLoader);

		TestClass testClass = getTestClass();

		List<FrameworkMethod> beforeFrameworkMethods =
			testClass.getAnnotatedMethods(Before.class);

		List<FrameworkMethod> afterFrameworkMethods =
			testClass.getAnnotatedMethods(After.class);

		List<String> arguments = createArguments(frameworkMethod);

		Class<?> clazz = testClass.getJavaClass();

		return new RunInNewJVMStatment(
			_classPath, arguments, clazz, beforeFrameworkMethods,
			frameworkMethod, afterFrameworkMethods);
	}

	protected ProcessCallable<Serializable> processProcessCallable(
		ProcessCallable<Serializable> processCallable,
		MethodKey testMethodKey) {

		return processCallable;
	}

	private static final int _HEARTBEAT_MAGIC_MUNBER = 253;

	private static final String _JPDA_OPTIONS =
		"-agentlib:jdwp=transport=dt_socket,address=8001,server=y,suspend=y";

	private static final int _START_SERVER_PORT = 10234;

	private static Log _log = LogFactoryUtil.getLog(
		NewJVMJUnitTestRunner.class);

	private String _classPath;

	private static class HeartbeatClientThread extends Thread {

		public HeartbeatClientThread(String name, int serverPort) {
			_serverPort = serverPort;

			setDaemon(true);
			setName(
				HeartbeatClientThread.class.getSimpleName().concat(
					StringPool.POUND).concat(name));
		}

		@Override
		public void run() {
			Socket socket = null;

			try {
				socket = new Socket(InetAddress.getLocalHost(), _serverPort);

				socket.shutdownInput();

				OutputStream outputStream = null;

				try {
					outputStream = socket.getOutputStream();
				}
				catch (IOException ioe) {
					return;
				}

				try {
					while (!_stop) {
						outputStream.write(_HEARTBEAT_MAGIC_MUNBER);

						try {
							sleep(1000);
						}
						catch (InterruptedException ie) {
						}
					}
				}
				catch (IOException ioe) {
					_log.error(
						"Main process socket peer closed unexpectedly", ioe);

					System.exit(10);
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
			finally {
				try {
					socket.close();
				}
				catch (IOException ioe) {
					_log.error(ioe, ioe);
				}
			}
		}

		public void shutdown() {
			_stop = true;
			interrupt();
		}

		private static Log _log = LogFactoryUtil.getLog(
			HeartbeatClientThread.class);

		private final int _serverPort;
		private volatile boolean _stop;

	}

	private static class HeartbeatServerThread extends Thread {

		public HeartbeatServerThread(String name, ServerSocket serverSocket) {
			_serverSocket = serverSocket;

			setDaemon(true);
			setName(
				HeartbeatServerThread.class.getSimpleName().concat(
					StringPool.POUND).concat(name));
		}

		@Override
		public void run() {
			try {
				_socket = _serverSocket.accept();

				_serverSocket.close();

				_socket.shutdownOutput();

				InputStream inputStream = null;

				try {
					inputStream = _socket.getInputStream();
				}
				catch (IOException ioe) {
					return;
				}

				int result = -1;

				while ((result = inputStream.read()) != -1) {
					if (result != _HEARTBEAT_MAGIC_MUNBER) {
						inputStream.close();

						_socket.close();
						_socket = null;

						break;
					}
				}
			}
			catch (IOException ioe) {
				_log.error(ioe, ioe);
			}
			finally {
				try {
					_serverSocket.close();
				}
				catch (IOException ioe) {
					_log.error(ioe, ioe);
				}

				if (_socket != null) {
					try {
						_socket.close();
						_socket = null;
					}
					catch (IOException ioe) {
						_log.error(ioe, ioe);
					}
				}
			}
		}

		public void shutdown() {
			interrupt();

			if (_socket != null) {
				try {
					_socket.close();
				}
				catch (IOException ioe) {
					_log.error(ioe, ioe);
				}
			}
		}

		private final ServerSocket _serverSocket;
		private volatile Socket _socket;

	}

	private static class TestProcessCallable
		implements ProcessCallable<Serializable> {

		public TestProcessCallable(
			String testClassName, List<MethodKey> beforeMethodKeys,
			MethodKey testMethodKey, List<MethodKey> afterMethodKeys,
			int serverPort) {

			_testClassName = testClassName;
			_beforeMethodKeys = beforeMethodKeys;
			_testMethodKey = testMethodKey;
			_afterMethodKeys = afterMethodKeys;
			_serverPort = serverPort;
		}

		public Serializable call() throws ProcessException {
			final HeartbeatClientThread heartbeatClientThread =
				new HeartbeatClientThread(toString(), _serverPort);

			Runtime runtime = Runtime.getRuntime();

			runtime.addShutdownHook(
				new Thread() {

					@Override
					public void run() {
						heartbeatClientThread.shutdown();
					}

				}
			);

			heartbeatClientThread.start();

			Thread currentThread = Thread.currentThread();

			ClassLoader contextClassLoader =
				currentThread.getContextClassLoader();

			try {
				Class<?> clazz = contextClassLoader.loadClass(_testClassName);

				Object object = clazz.newInstance();

				for (MethodKey beforeMethodKey : _beforeMethodKeys) {
					_invoke(beforeMethodKey, object);
				}

				_invoke(_testMethodKey, object);

				for (MethodKey afterMethodKey : _afterMethodKeys) {
					_invoke(afterMethodKey, object);
				}
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}

			return StringPool.BLANK;
		}

		@Override
		public String toString() {
			StringBundler sb = new StringBundler(4);

			sb.append(_testClassName);
			sb.append(StringPool.PERIOD);
			sb.append(_testMethodKey.getMethodName());
			sb.append("()");

			return sb.toString();
		}

		private void _invoke(MethodKey methodKey, Object object)
			throws Exception {

			MethodHandler methodHandler = new MethodHandler(methodKey);

			methodHandler.invoke(object);
		}

		private List<MethodKey> _afterMethodKeys;
		private List<MethodKey> _beforeMethodKeys;
		private int _serverPort;
		private String _testClassName;
		private MethodKey _testMethodKey;

	}

	private class RunInNewJVMStatment extends Statement {

		public RunInNewJVMStatment(
			String classPath, List<String> arguments, Class<?> testClass,
			List<FrameworkMethod> beforeFrameworkMethods,
			FrameworkMethod testFrameworkMethod,
			List<FrameworkMethod> afterFrameworkMethods) {

			_classPath = classPath;
			_arguments = arguments;
			_testClassName = testClass.getName();

			_beforeMethodKeys = new ArrayList<MethodKey>(
				beforeFrameworkMethods.size());

			for (FrameworkMethod frameworkMethod : beforeFrameworkMethods) {
				_beforeMethodKeys.add(
					new MethodKey(frameworkMethod.getMethod()));
			}

			_testMethodKey = new MethodKey(testFrameworkMethod.getMethod());

			_afterMethodKeys = new ArrayList<MethodKey>(
				afterFrameworkMethods.size());

			for (FrameworkMethod frameworkMethod : afterFrameworkMethods) {
				_afterMethodKeys.add(
					new MethodKey(frameworkMethod.getMethod()));
			}
		}

		@Override
		public void evaluate() throws Throwable {
			ServerSocket serverSocket = createServerSocket();

			ProcessCallable<Serializable> processCallable =
				new TestProcessCallable(
					_testClassName, _beforeMethodKeys, _testMethodKey,
					_afterMethodKeys, serverSocket.getLocalPort());

			HeartbeatServerThread heartbeatServerThread =
				new HeartbeatServerThread(
					processCallable.toString(), serverSocket);

			heartbeatServerThread.start();

			processCallable = processProcessCallable(
				processCallable, _testMethodKey);

			try {
				ProcessExecutor.execute(
					processCallable, _classPath, _arguments);
			}
			catch (ProcessException pe) {
				Throwable cause = pe.getCause();

				while ((cause instanceof ProcessException) ||
					(cause instanceof InvocationTargetException)) {

					cause = cause.getCause();
				}

				throw cause;
			}
			finally {
				heartbeatServerThread.shutdown();
			}
		}

		private List<MethodKey> _afterMethodKeys;
		private List<String> _arguments;
		private List<MethodKey> _beforeMethodKeys;
		private String _classPath;
		private String _testClassName;
		private MethodKey _testMethodKey;

	}

}