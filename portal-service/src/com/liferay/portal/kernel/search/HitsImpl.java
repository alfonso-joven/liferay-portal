/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.json.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 */
public class HitsImpl implements Hits {

	public HitsImpl() {
	}

	@Override
	public Document doc(int n) {
		return _docs[n];
	}

	@Override
	@JSON
	public Document[] getDocs() {
		return _docs;
	}

	@Override
	public int getLength() {
		return _length;
	}

	@Override
	@JSON(include = false)
	public Query getQuery() {
		return _query;
	}

	@Override
	@JSON
	public String[] getQueryTerms() {
		return _queryTerms;
	}

	@Override
	@JSON
	public float[] getScores() {
		return _scores;
	}

	@Override
	public float getSearchTime() {
		return _searchTime;
	}

	@Override
	@JSON
	public String[] getSnippets() {
		return _snippets;
	}

	@Override
	public long getStart() {
		return _start;
	}

	@Override
	public float score(int n) {
		return _scores[n];
	}

	@Override
	public void setDocs(Document[] docs) {
		_docs = docs;
	}

	@Override
	public void setLength(int length) {
		_length = length;
	}

	@Override
	public void setQuery(Query query) {
		_query = query;
	}

	@Override
	public void setQueryTerms(String[] queryTerms) {
		_queryTerms = queryTerms;
	}

	@Override
	public void setScores(float[] scores) {
		_scores = scores;
	}

	@Override
	public void setScores(Float[] scores) {
		float[] primScores = new float[scores.length];

		for (int i = 0; i < scores.length; i++) {
			primScores[i] = scores[i].floatValue();
		}

		setScores(primScores);
	}

	@Override
	public void setSearchTime(float time) {
		_searchTime = time;
	}

	@Override
	public void setSnippets(String[] snippets) {
		_snippets = snippets;
	}

	@Override
	public void setStart(long start) {
		_start = start;
	}

	@Override
	public String snippet(int n) {
		return _snippets[n];
	}

	@Override
	public List<Document> toList() {
		List<Document> subset = new ArrayList<Document>(_docs.length);

		for (Document _doc : _docs) {
			subset.add(_doc);
		}

		return subset;
	}

	private Document[] _docs;
	private int _length;
	private Query _query;
	private String[] _queryTerms;
	private float[] _scores = new float[0];
	private float _searchTime;
	private String[] _snippets = {};
	private long _start;

}