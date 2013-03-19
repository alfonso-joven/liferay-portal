package ${seleniumBuilderContext.getTestCasePackageName(testCaseName)};

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.SeleniumUtil;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

<#assign rootElement = seleniumBuilderContext.getTestCaseRootElement(testCaseName)>

<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(rootElement, "action")>

<#list childElementAttributeValues as childElementAttributeValue>
	import ${seleniumBuilderContext.getActionClassName(childElementAttributeValue)};
</#list>

<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(rootElement, "macro")>

<#list childElementAttributeValues as childElementAttributeValue>
	import ${seleniumBuilderContext.getMacroClassName(childElementAttributeValue)};
</#list>

public class ${seleniumBuilderContext.getTestCaseSimpleClassName(testCaseName)} extends BaseTestCase {

	@Override
	public void setUp() throws Exception {
		selenium = SeleniumUtil.getSelenium();

		<#if rootElement.element("test-case-setup")??>
			<#assign testCaseSetupElement = rootElement.element("test-case-setup")>

			<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(testCaseSetupElement, "action")>

			<#list childElementAttributeValues as childElementAttributeValue>
				${childElementAttributeValue}Action ${seleniumBuilderFileUtil.getVariableName(childElementAttributeValue)}Action = new ${childElementAttributeValue}Action(selenium);
			</#list>

			<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(testCaseSetupElement, "macro")>

			<#list childElementAttributeValues as childElementAttributeValue>
				${childElementAttributeValue}Macro ${seleniumBuilderFileUtil.getVariableName(childElementAttributeValue)}Macro = new ${childElementAttributeValue}Macro(selenium);
			</#list>
		</#if>
	}

	<#assign testCaseCommandElements = rootElement.elements("test-case-command")>

	<#list testCaseCommandElements as testCaseCommandElement>
		<#assign testCaseCommandName = testCaseCommandElement.attributeValue("name")>

		public void test${testCaseCommandName}() throws Exception {
			<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(testCaseCommandElement, "action")>

			<#list childElementAttributeValues as childElementAttributeValue>
				${childElementAttributeValue}Action ${seleniumBuilderFileUtil.getVariableName(childElementAttributeValue)}Action = new ${childElementAttributeValue}Action(selenium);
			</#list>

			<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(testCaseCommandElement, "macro")>

			<#list childElementAttributeValues as childElementAttributeValue>
				${childElementAttributeValue}Macro ${seleniumBuilderFileUtil.getVariableName(childElementAttributeValue)}Macro = new ${childElementAttributeValue}Macro(selenium);
			</#list>
		}
	</#list>

	@Override
	public void tearDown() throws Exception {
		<#if rootElement.element("test-case-teardown")??>
			<#assign testCaseTeardownElement = rootElement.element("test-case-teardown")>

			<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(testCaseTeardownElement, "action")>

			<#list childElementAttributeValues as childElementAttributeValue>
				${childElementAttributeValue}Action ${seleniumBuilderFileUtil.getVariableName(childElementAttributeValue)}Action = new ${childElementAttributeValue}Action(selenium);
			</#list>

			<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(testCaseTeardownElement, "macro")>

			<#list childElementAttributeValues as childElementAttributeValue>
				${childElementAttributeValue}Macro ${seleniumBuilderFileUtil.getVariableName(childElementAttributeValue)}Macro = new ${childElementAttributeValue}Macro(selenium);
			</#list>
		</#if>
	}

}