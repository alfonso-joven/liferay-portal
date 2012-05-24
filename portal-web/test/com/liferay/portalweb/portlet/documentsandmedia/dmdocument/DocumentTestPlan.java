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

package com.liferay.portalweb.portlet.documentsandmedia.dmdocument;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.adddmfolderdocument.AddDMFolderDocumentTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.adddmfolderdocumentmultiple.AddDMFolderDocumentMultipleTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.adddmfolderdocumentnull.AddDMFolderDocumentNullTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.adddmfolderdocumenttitleduplicate.AddDMFolderDocumentTitleDuplicateTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.adddmfolderdocumenttitlenull.AddDMFolderDocumentTitleNullTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.adddmsubfolderdocument.AddDMSubfolderDocumentTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.comparedmfolderdocumentversion.CompareDMFolderDocumentVersionTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.deletedmfolderdocument.DeleteDMFolderDocumentTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.editdmfolderdocument.EditDMFolderDocumentTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.editdmfolderdocumenttitleapostrophe.EditDMFolderDocumentTitleApostropheTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.editdmfolderdocumenttitlebackslash.EditDMFolderDocumentTitleBackSlashTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.editdmfolderdocumenttitleclosebracket.EditDMFolderDocumentTitleCloseBracketTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.editdmfolderdocumenttitlecolon.EditDMFolderDocumentTitleColonTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.editdmfolderdocumenttitleforwardslash.EditDMFolderDocumentTitleForwardSlashTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.editdmfolderdocumenttitlegreaterthan.EditDMFolderDocumentTitleGreaterThanTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.editdmfolderdocumenttitlelessthan.EditDMFolderDocumentTitleLessThanTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.editdmfolderdocumenttitleopenbracket.EditDMFolderDocumentTitleOpenBracketTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.editdmfolderdocumenttitlepipe.EditDMFolderDocumentTitlePipeTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.editdmfolderdocumenttitlequestion.EditDMFolderDocumentTitleQuestionTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.editdmfolderdocumenttitlequote.EditDMFolderDocumentTitleQuoteTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.editdmfolderdocumenttitlestar.EditDMFolderDocumentTitleStarTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.guestviewdmdocumentguestviewoff.Guest_ViewDMDocumentGuestViewOffTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.lockdmfolderdocument.LockDMFolderDocumentTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.movedmfolderdocumentduplicatetofolder.MoveDMFolderDocumentDuplicateToFolderTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.movedmfolderdocumenttofolder.MoveDMFolderDocumentToFolderTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.searchdmfolderdocument.SearchDMFolderDocumentTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.unlockdmfolderdocument.UnlockDMFolderDocumentTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.viewdmfolderdocumentactions.ViewDMFolderDocumentActionsTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.viewdmfolderdocumentmydocuments.ViewDMFolderDocumentMyDocumentsTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.viewdmfolderdocumentrecentdocuments.ViewDMFolderDocumentRecentDocumentsTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DocumentTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddDMFolderDocumentTests.suite());
		testSuite.addTest(AddDMFolderDocumentNullTests.suite());
		testSuite.addTest(AddDMFolderDocumentMultipleTests.suite());
		testSuite.addTest(AddDMFolderDocumentNullTests.suite());
		testSuite.addTest(AddDMFolderDocumentTitleDuplicateTests.suite());
		testSuite.addTest(AddDMFolderDocumentTitleNullTests.suite());
		testSuite.addTest(AddDMSubfolderDocumentTests.suite());
		testSuite.addTest(CompareDMFolderDocumentVersionTests.suite());
		testSuite.addTest(DeleteDMFolderDocumentTests.suite());
		testSuite.addTest(EditDMFolderDocumentTitleApostropheTests.suite());
		testSuite.addTest(EditDMFolderDocumentTitleBackSlashTests.suite());
		testSuite.addTest(EditDMFolderDocumentTitleCloseBracketTests.suite());
		testSuite.addTest(EditDMFolderDocumentTitleColonTests.suite());
		testSuite.addTest(EditDMFolderDocumentTitleForwardSlashTests.suite());
		testSuite.addTest(EditDMFolderDocumentTitleGreaterThanTests.suite());
		testSuite.addTest(EditDMFolderDocumentTitleLessThanTests.suite());
		testSuite.addTest(EditDMFolderDocumentTitleOpenBracketTests.suite());
		testSuite.addTest(EditDMFolderDocumentTitlePipeTests.suite());
		testSuite.addTest(EditDMFolderDocumentTitleQuestionTests.suite());
		testSuite.addTest(EditDMFolderDocumentTitleQuoteTests.suite());
		testSuite.addTest(EditDMFolderDocumentTitleStarTests.suite());
		testSuite.addTest(EditDMFolderDocumentTests.suite());
		testSuite.addTest(Guest_ViewDMDocumentGuestViewOffTests.suite());
		testSuite.addTest(LockDMFolderDocumentTests.suite());
		testSuite.addTest(MoveDMFolderDocumentDuplicateToFolderTests.suite());
		testSuite.addTest(MoveDMFolderDocumentToFolderTests.suite());
		testSuite.addTest(SearchDMFolderDocumentTests.suite());
		testSuite.addTest(UnlockDMFolderDocumentTests.suite());
		testSuite.addTest(ViewDMFolderDocumentActionsTests.suite());
		testSuite.addTest(ViewDMFolderDocumentMyDocumentsTests.suite());
		testSuite.addTest(ViewDMFolderDocumentRecentDocumentsTests.suite());

		return testSuite;
	}

}