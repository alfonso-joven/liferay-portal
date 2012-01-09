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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.permission.PermissionThreadLocal;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Michael C. Han
 */
public class SearchEngineUtil {

	/**
	 * @deprecated Use {@link
	 *             com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}.
	 */
	public static final int ALL_POS = -1;

	public static final String GENERIC_ENGINE_ID = "GENERIC_ENGINE";

	public static final String SYSTEM_ENGINE_ID = "SYSTEM_ENGINE";

	/**
	 * @deprecated Use {@link
	 *             addDocument(String searchEngineId, long companyId, Document document)}.
	 */
	public static void addDocument(long companyId, Document document)
		throws SearchException {

		addDocument(getSearchEngine(document), companyId, document);
	}

	public static void addDocument(
			String searchEngineId, long companyId, Document document)
		throws SearchException {

		if (isIndexReadOnly()) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Add document " + document.toString());
		}

		_searchPermissionChecker.addPermissionFields(companyId, document);

		SearchEngine searchEngine = _searchEngines.get(searchEngineId);

		IndexWriter indexWriter = searchEngine.getIndexWriter();

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setSearchEngineId(searchEngineId);

		indexWriter.addDocument(searchContext, document);
	}

	/**
	 * @deprecated Use {@link
	 *     addDocuments(String searchEngineId, long companyId,Collection<Document> documents)}.
	 */
	public static void addDocuments(
			long companyId, Collection<Document> documents)
		throws SearchException {
		addDocuments(getSearchEngine(documents), companyId, documents);
	}

	public static void addDocuments(
			String searchEngineId, long companyId,
			Collection<Document> documents)
		throws SearchException {

		if (isIndexReadOnly() || (documents == null) || documents.isEmpty()) {
			return;
		}

		for (Document document : documents) {
			if (_log.isDebugEnabled()) {
				_log.debug("Add document " + document.toString());
			}

			_searchPermissionChecker.addPermissionFields(companyId, document);
		}

		SearchEngine searchEngine = _searchEngines.get(searchEngineId);

		IndexWriter indexWriter = searchEngine.getIndexWriter();

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setSearchEngineId(searchEngineId);

		indexWriter.addDocuments(searchContext, documents);
	}

	public static void addSearchEngine(SearchEngine searchEngine) {
		_searchEngines.put(searchEngine.getName(), searchEngine);
	}

	/**
	 * @deprecated Use {@link
	 *     deleteDocument(String searchEngineId, long companyId, String uid)}.
	 */
	public static void deleteDocument(long companyId, String uid)
		throws SearchException {

		//TODO. Dirty fix so delete in unknown searchEngineID goes to all engines.
		for (String searchEngineId:_searchEngines.keySet()) {
			deleteDocument(searchEngineId, companyId, uid);
		}
	}

	public static void deleteDocument(
			String searchEngineId, long companyId, String uid)
		throws SearchException {

		if (isIndexReadOnly()) {
			return;
		}

		SearchEngine searchEngine = _searchEngines.get(searchEngineId);

		IndexWriter indexWriter = searchEngine.getIndexWriter();

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setSearchEngineId(searchEngineId);

		indexWriter.deleteDocument(searchContext, uid);
	}

	/**
	 * @deprecated Use {@link
	 *     deleteDocuments(String searchEngineId, long companyId, Collection<String> uids)}.
	 */
	public static void deleteDocuments(long companyId, Collection<String> uids)
		throws SearchException {

		//TODO. Dirty fix so delete in unknown searchEngineID goes to all engines.
		for (String searchEngineId:_searchEngines.keySet()) {
			deleteDocuments(searchEngineId, companyId, uids);
		}
	}

	public static void deleteDocuments(
			String searchEngineId, long companyId, Collection<String> uids)
		throws SearchException {

		if (isIndexReadOnly() || (uids == null) || uids.isEmpty()) {
			return;
		}

		SearchEngine searchEngine = _searchEngines.get(searchEngineId);

		IndexWriter indexWriter = searchEngine.getIndexWriter();

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setSearchEngineId(searchEngineId);

		indexWriter.deleteDocuments(searchContext, uids);
	}

	/**
	 * @deprecated Use {@link
	 *     deletePortletDocuments(String searchEngineId, long companyId, String portletId)}.
	 */
	public static void deletePortletDocuments(long companyId, String portletId)
		throws SearchException {

		//TODO. Dirty fix so delete in unknown searchEngineID goes to all engines.
		for (String searchEngineId:_searchEngines.keySet()) {
			deletePortletDocuments(searchEngineId, companyId, portletId);
		}
	}

	public static void deletePortletDocuments(
			String searchEngineId, long companyId, String portletId)
		throws SearchException {

		if (isIndexReadOnly()) {
			return;
		}

		SearchEngine searchEngine = _searchEngines.get(searchEngineId);

		if (searchEngine == null) {
			return;
		}

		IndexWriter indexWriter = searchEngine.getIndexWriter();

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setSearchEngineId(searchEngineId);

		indexWriter.deletePortletDocuments(searchContext, portletId);
	}

	public static String[] getEntryClassNames() {
		Set<String> assetEntryClassNames = new HashSet<String>();

		for (Indexer indexer : IndexerRegistryUtil.getIndexers()) {
			for (String className : indexer.getClassNames()) {
				if (!_excludedEntryClassNames.contains(className)) {
					assetEntryClassNames.add(className);
				}
			}
		}

		return assetEntryClassNames.toArray(
			new String[assetEntryClassNames.size()]);
	}

	/**
 	 * @deprecated Search Engine Selection needs a searchEngineId. Use {@link
 	 *     getSearchEngine(String searchEngineId)}.
	 */
	public static SearchEngine getSearchEngine() {
		return getSearchEngine(SYSTEM_ENGINE_ID);
	}

	private static String getSearchEngine(Document document) {
	    String documentClassName = document.get("entryClassName");

	    Indexer indexer = IndexerRegistryUtil.getIndexer(documentClassName);

		String searchEngineId = indexer.getSearchEngineId();

		if (_log.isDebugEnabled()) {
			_log.debug("SearchEngineId for ["+indexer.getClass()+"] is: " + searchEngineId);
		}

		return searchEngineId;
	}

 	private static String getSearchEngine(Collection<Document> documents) {
		if (!documents.isEmpty()) {
			Document document = documents.iterator().next();

			return getSearchEngine(document);
		} else {
			return SYSTEM_ENGINE_ID;
		}
	}

	public static SearchEngine getSearchEngine(String searchEngineId) {
		return _searchEngines.get(searchEngineId);
	}

	public static SearchPermissionChecker getSearchPermissionChecker() {
		return _searchPermissionChecker;
	}

	public static String getSearchReaderDestinationName(String searchEngineId) {
		return DestinationNames.SEARCH_READER.concat(StringPool.SLASH).concat(
			searchEngineId);
	}

	public static String getSearchWriterDestinationName(String searchEngineId) {
		return DestinationNames.SEARCH_WRITER.concat(StringPool.SLASH).concat(
			searchEngineId);
	}

	public static boolean isIndexReadOnly() {
		return _indexReadOnly;
	}

        public static SearchEngine removeSearchEngine(String searchEngineName) {
		return _searchEngines.remove(searchEngineName);
	}

	@Deprecated
	public static Hits search(
			long companyId, long[] groupIds, long userId, String className,
			Query query, int start, int end)
		throws SearchException {

                //TODO should get the searchEngineID based on what?
		SearchContext searchContext = new SearchContext();
                searchContext.setSearchEngineId(SearchEngineUtil.SYSTEM_ENGINE_ID);

		if (userId > 0) {
			query = _searchPermissionChecker.getPermissionQuery(
				companyId, groupIds, userId, className, query, searchContext);
		}

		return search(
			companyId, query, SortFactoryUtil.getDefaultSorts(), start, end);
	}

	@Deprecated
	public static Hits search(
			long companyId, long[] groupIds, long userId, String className,
			Query query, Sort sort, int start, int end)
		throws SearchException {

                //TODO should get the searchEngineID based on what?
		SearchContext searchContext = new SearchContext();
                searchContext.setSearchEngineId(SearchEngineUtil.SYSTEM_ENGINE_ID);

		if (userId > 0) {
			query = _searchPermissionChecker.getPermissionQuery(
				companyId, groupIds, userId, className, query, searchContext);
		}

		return search(companyId, query, sort, start, end);
	}

	@Deprecated
	public static Hits search(
			long companyId, long[] groupIds, long userId, String className,
			Query query, Sort[] sorts, int start, int end)
		throws SearchException {

                //TODO should get the searchEngineID based on what?
		SearchContext searchContext = new SearchContext();
		searchContext.setSearchEngineId(SearchEngineUtil.SYSTEM_ENGINE_ID);

		if (userId > 0) {
			query = _searchPermissionChecker.getPermissionQuery(
				companyId, groupIds, userId, className, query, searchContext);
		}

		return search(companyId, query, sorts, start, end);
	}

	@Deprecated
	public static Hits search(long companyId, Query query, int start, int end)
		throws SearchException {

		return search(SYSTEM_ENGINE_ID, companyId, query, start, end);
	}

	@Deprecated
	public static Hits search(
			long companyId, Query query, Sort sort, int start, int end)
		throws SearchException {

		return search(SYSTEM_ENGINE_ID, companyId, query, sort, start, end);
	}

	@Deprecated
	public static Hits search(
			long companyId, Query query, Sort[] sorts, int start, int end)
		throws SearchException {

		return search(SYSTEM_ENGINE_ID, companyId, query, sorts, start, end);
	}

	public static Hits search(SearchContext searchContext, Query query)
		throws SearchException {

		if (_log.isDebugEnabled()) {
			_log.debug("Search query " + query.toString());
		}

		SearchEngine searchEngine = _searchEngines.get(
			searchContext.getSearchEngineId());

		IndexSearcher indexSearcher = searchEngine.getIndexSearcher();

		return indexSearcher.search(searchContext, query);
	}

	public static Hits search(
			String searchEngineId, long companyId, Query query, int start,
			int end)
		throws SearchException {

		if (_log.isDebugEnabled()) {
			_log.debug("Search query " + query.toString());
		}

		SearchEngine searchEngine = _searchEngines.get(searchEngineId);

		IndexSearcher indexSearcher = searchEngine.getIndexSearcher();

		return indexSearcher.search(
			searchEngineId, companyId, query, SortFactoryUtil.getDefaultSorts(),
			start, end);
	}

	public static Hits search(
			String searchEngineId, long companyId, Query query, Sort sort,
			int start, int end)
		throws SearchException {

		if (_log.isDebugEnabled()) {
			_log.debug("Search query " + query.toString());
		}

		SearchEngine searchEngine = _searchEngines.get(searchEngineId);

		IndexSearcher indexSearcher = searchEngine.getIndexSearcher();

		return indexSearcher.search(
			searchEngineId, companyId, query, new Sort[] {sort}, start, end);
	}

	public static Hits search(
			String searchEngineId, long companyId, Query query, Sort[] sorts,
			int start, int end)
		throws SearchException {

		if (_log.isDebugEnabled()) {
			_log.debug("Search query " + query.toString());
		}

		SearchEngine searchEngine = _searchEngines.get(searchEngineId);

		IndexSearcher indexSearcher = searchEngine.getIndexSearcher();

		return indexSearcher.search(
			searchEngineId, companyId, query, sorts, start, end);
	}

	public static void setIndexReadOnly(boolean indexReadOnly) {
		_indexReadOnly = indexReadOnly;
	}

	/**
	 * @deprecated Use {@link
	 *     updateDocument(String searchEngineId, long companyId, Document document)}.
	 */
	public static void updateDocument(long companyId, Document document)
		throws SearchException {

		updateDocument(getSearchEngine(document), companyId, document);
	}

	public static void updateDocument(
			String searchEngineId, long companyId, Document document)
		throws SearchException {

		if (isIndexReadOnly()) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + document.toString());
		}

		_searchPermissionChecker.addPermissionFields(companyId, document);

		SearchEngine searchEngine = _searchEngines.get(searchEngineId);

		IndexWriter indexWriter = searchEngine.getIndexWriter();

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setSearchEngineId(searchEngineId);

		indexWriter.updateDocument(searchContext, document);
	}

	/**
	 * @deprecated Use {@link
	 *     updateDocuments(String searchEngineId, long companyId,Collection<Document> documents)}.
	 */
	public static void updateDocuments(
			long companyId, Collection<Document> documents)
		throws SearchException {
		updateDocuments(getSearchEngine(documents), companyId, documents);
	}

	public static void updateDocuments(
			String searchEngineId, long companyId,
			Collection<Document> documents)
		throws SearchException {

		if (isIndexReadOnly() || (documents == null) || documents.isEmpty()) {
			return;
		}

		for (Document document : documents) {
			if (_log.isDebugEnabled()) {
				_log.debug("Document " + document.toString());
			}

			_searchPermissionChecker.addPermissionFields(companyId, document);
		}

		SearchEngine searchEngine = _searchEngines.get(searchEngineId);

		IndexWriter indexWriter = searchEngine.getIndexWriter();

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setSearchEngineId(searchEngineId);

		indexWriter.updateDocuments(searchContext, documents);
	}

	public static void updatePermissionFields(long resourceId) {
		if (isIndexReadOnly() || !PermissionThreadLocal.isFlushEnabled()) {
			return;
		}

		_searchPermissionChecker.updatePermissionFields(resourceId);
	}

	public static void updatePermissionFields(String name, String primKey) {
		if (isIndexReadOnly() || !PermissionThreadLocal.isFlushEnabled()) {
			return;
		}

		_searchPermissionChecker.updatePermissionFields(name, primKey);
	}

	public void setExcludedEntryClassNames(
		List<String> excludedEntryClassNames) {

		_excludedEntryClassNames.addAll(excludedEntryClassNames);
	}

	@Deprecated
	public void setSearchEngine(SearchEngine searchEngine) {
        //TODO Might need some vodoo in case no engines is specified.
		_searchEngines.put(SYSTEM_ENGINE_ID, searchEngine);
	}

	public void setSearchEngine(String searchEngineId, SearchEngine searchEngine) {
		if (_searchEngines.containsKey(searchEngineId)) {
			//TODO throw execption like "SearchEngineAllreadyRegistered"
		} else {
			_searchEngines.put(searchEngineId, searchEngine);
		}
	}

	public void setSearchEngines(Map<String, SearchEngine> searchEngines) {
		_searchEngines.putAll(searchEngines);
	}

	public void setSearchPermissionChecker(
		SearchPermissionChecker searchPermissionChecker) {

		_searchPermissionChecker = searchPermissionChecker;
	}

	private static Log _log = LogFactoryUtil.getLog(SearchEngineUtil.class);

	private static Set<String> _excludedEntryClassNames = new HashSet<String>();
	private static boolean _indexReadOnly = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.INDEX_READ_ONLY));
	private static Map<String, SearchEngine> _searchEngines =
		new ConcurrentHashMap<String, SearchEngine>();
	private static SearchPermissionChecker _searchPermissionChecker;

}