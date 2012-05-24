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

package com.liferay.portalweb.portlet.documentsandmedia.dmfolder;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.documentsandmedia.dmfolder.adddmfolder.AddDMFolderTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmfolder.adddmfoldermultiple.AddDMFolderMultipleTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmfolder.adddmfoldernameduplicate.AddDMFolderNameDuplicateTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmfolder.adddmfoldernamenull.AddDMFolderNameNullTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmfolder.adddmfoldernamespace.AddDMFolderNameSpaceTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmfolder.adddmfoldernamesymbol.AddDMFolderNameSymbolTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmfolder.adddmsubfolder.AddDMSubfolderTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmfolder.adddmsubfoldermultiple.AddDMSubfolderMultipleTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmfolder.adddmsubfoldernamedocumentname.AddDMSubfolderNameDocumentNameTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmfolder.adddmsubfoldernameimagename.AddDMSubfolderNameImageNameTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmfolder.deletedmfolder.DeleteDMFolderTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmfolder.deletedmsubfolder.DeleteDMSubfolderTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmfolder.editdmfolder.EditDMFolderTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmfolder.editdmsubfolder.EditDMSubfolderTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmfolder.movedmsubfoldertofolder2.MoveDMSubfolderToFolder2Tests;
import com.liferay.portalweb.portlet.documentsandmedia.dmfolder.viewdmfolderactions.ViewDMFolderActionsTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class FolderTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddDMSubfolderNameImageNameTests.suite());
		testSuite.addTest(AddDMFolderTests.suite());
		testSuite.addTest(AddDMFolderMultipleTests.suite());
		testSuite.addTest(AddDMFolderNameDuplicateTests.suite());
		testSuite.addTest(AddDMFolderNameNullTests.suite());
		testSuite.addTest(AddDMFolderNameSpaceTests.suite());
		testSuite.addTest(AddDMFolderNameSymbolTests.suite());
		testSuite.addTest(AddDMSubfolderTests.suite());
		testSuite.addTest(AddDMSubfolderMultipleTests.suite());
		testSuite.addTest(AddDMSubfolderNameDocumentNameTests.suite());
		testSuite.addTest(DeleteDMFolderTests.suite());
		testSuite.addTest(DeleteDMSubfolderTests.suite());
		testSuite.addTest(EditDMFolderTests.suite());
		testSuite.addTest(EditDMSubfolderTests.suite());
		testSuite.addTest(MoveDMSubfolderToFolder2Tests.suite());
		testSuite.addTest(ViewDMFolderActionsTests.suite());

		return testSuite;
	}

}