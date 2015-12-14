package com.qait.keywords;

import static com.qait.automation.utils.YamlReader.getMapValue;
import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.Map;

public class YamlInformationProvider {

	Map<String, Object> userInfoMap;

	public YamlInformationProvider() {
	}

	public YamlInformationProvider(Map<String, Object> userInfoMap) {
		this.userInfoMap = userInfoMap;
	}

	public String getEduEmpInfo(String infoType) {
		return getMapValue(this.userInfoMap, "education-and-employment" + "."
				+ infoType);
	}

	public String getEduEmpInfo(String eduEmoInfoType, String subInfo) {
		String subToken = "education-and-employment" + "." + eduEmoInfoType
				+ "." + subInfo;
		return getMapValue(this.userInfoMap, subToken);
	}

	public String getCreditCardInfo(String infoType) {
		return getYamlValue("creditCardInfo" + "." + infoType);
	}

	public String getContactInfoName(String contactInfoType) {
		return getMapValue(this.userInfoMap, "contact-information."
				+ contactInfoType);
	}

	public String getContactInfoName(String contactInfoType, String subInfo) {
		String subToken = "contact-information." + contactInfoType + "."
				+ subInfo;
		return getMapValue(this.userInfoMap, subToken);
	}

	public String getBenefitsInfo(String infoType) {
		return getMapValue(this.userInfoMap, "Benefits" + "." + infoType);

	}

	public String getASM_StoreShippingAddress(String infoType) {
		return getMapValue(this.userInfoMap, "shippingAddress" + "." + infoType);
	}

	public String getASM_StorePaymentInfo(String infoType) {
		return getMapValue(this.userInfoMap, "paymentInformation" + "."
				+ infoType);
	}

	public String getASM_OMASmokeInfo(String infoType) {
		return getMapValue(this.userInfoMap, infoType);
	}

	public String get_AACTInfo(String infoType) {
		return getMapValue(this.userInfoMap, infoType);
	}

	public String get_AACTContactInfo(String infoType) {
		return getMapValue(this.userInfoMap, "contactInformation" + "."
				+ infoType);
	}

	public String get_AACTAboutYouInfo(String infoType) {
		return getMapValue(this.userInfoMap, "aboutYou" + "." + infoType);
	}

	public String get_AACT_CreditCardInfo(String infoType) {
		return getMapValue(this.userInfoMap, "creditCardInfo" + "." + infoType);
	}

	public String getASM_email_LoginInfo(String infoType) {
		return getMapValue(this.userInfoMap, "LoginInfo" + "." + infoType);
	}

	public String getASM_fellowNominated_LoginInfo(String infoType) {
		return getMapValue(this.userInfoMap, "LoginInfo" + "." + infoType);
	}

	public String getASM_fellowNominated(String infoType) {
		return getMapValue(this.userInfoMap, infoType);
	}

	public String getASM_fellowNominated_EduDetails(String infoType) {
		return getMapValue(this.userInfoMap, "EducationDetails" + "."
				+ infoType);
	}

	public String getASM_fellowNominated_ProfDetails(String infoType) {
		return getMapValue(this.userInfoMap, "ProfessionalHistory" + "."
				+ infoType);
	}

	public String getASM_fellowNominated_ProfOrgDetails(String infoType) {
		return getMapValue(this.userInfoMap,
				"ProfessionalOrganizationAffiliation" + "." + infoType);
	}

	public String getASM_fellowNominated_HonorAndAwardsDetails(String infoType) {
		return getMapValue(this.userInfoMap, "HonorAndAwards" + "." + infoType);
	}

	public String getASM_fellowNominated_SelectSupportAndUploadLetters(
			String infoType) {
		return getMapValue(this.userInfoMap, "SelectSupportAndUploadLetters"
				+ "." + infoType);
	}

	public String getReinstateMember_centralizedOrderEntry(String infoType) {
		return getMapValue(this.userInfoMap, "centralizedOrderEntry" + "."
				+ infoType);
	}

	public String getASM_Donate(String infoType) {
		return getMapValue(this.userInfoMap, infoType);
	}

	public String getASM_MakeDonate(String infoType) {
		return getMapValue(this.userInfoMap, "makeDonation." + infoType);
	}

	public String getASM_Donate_ContactInfo(String infoType) {
		return getMapValue(this.userInfoMap, "ContactInfo." + infoType);
	}

	public String getASM_Donate_ConfirmYourDonation(String infoType) {
		return getMapValue(this.userInfoMap, "ConfirmYourDonation." + infoType);
	}

	public String getASM_GivingGreen(String infoType) {
		return getMapValue(this.userInfoMap, infoType);
	}

	public String getASM_GivingGreen_ContactInfo(String infoType) {
		return getMapValue(this.userInfoMap, "ContactInfo." + infoType);
	}

	public String getASM_OMR(String infoType) {
		return getMapValue(this.userInfoMap, infoType);
	}

	public String getASM_OMR_CreditCardDate(String infoType) {
		return getMapValue(this.userInfoMap, "CreditCardDate." + infoType);
	}

	public String getASM_MGMInfo(String infoType) {
		return getMapValue(this.userInfoMap, infoType);
	}

	public String getASM_MGM_memberDetailsToInvite(String infoType) {
		return getMapValue(this.userInfoMap, "memberDetailsToInvite."
				+ infoType);
	}

	public String getASM_MeetingInfo(String infoType) {
		return getMapValue(this.userInfoMap, infoType);
	}

	public String getASM_MemberNumberLookupInfo(String infoType) {
		return getMapValue(this.userInfoMap, infoType);
	}

	public String getASM_PUBSInfo(String infoType) {
		return getMapValue(this.userInfoMap, infoType);
	}

	public String getASM_NominateInfo(String infoType) {
		return getMapValue(this.userInfoMap, infoType);
	}

	public String getASM_EGiftInfo(String infoType) {
		return getMapValue(this.userInfoMap, infoType);
	}

	public String getAuthenticationInfo(String infoType) {
		return getYamlValue("Authentication" + "." + infoType);
	}

	public String getSubscriptionInfo(String infoType) {
		return getMapValue(this.userInfoMap, infoType);
	}

	public String getSubsFul_cenOrdEntry(String infoType) {
		return getMapValue(this.userInfoMap, "centralizedOrderEntry" + "."
				+ infoType);
	}

	public String getSubsFul_Schedule(String infoType) {
		return getMapValue(this.userInfoMap, "subscriptionDetailsForSchedule"
				+ "." + infoType);
	}

	public String getSubsFul_Committed(String infoType) {
		return getMapValue(this.userInfoMap, "subscriptionDetailsForCommitted"
				+ "." + infoType);
	}

	public String getSubsFul_PreviewComplete(String infoType) {
		return getMapValue(this.userInfoMap,
				"subscriptionDetailsForPreviewComplete" + "." + infoType);
	}

	public String getMemRenewalAddACSRenewalCycle(String infoType) {
		return getMapValue(this.userInfoMap, "addACSRenewalCycle" + "."
				+ infoType);
	}

	public String getMemRenewalFirstMember(String infoType) {
		return getMapValue(this.userInfoMap, "firstMember" + "." + infoType);
	}

	public String getMemRenewalSecondMember(String infoType) {
		return getMapValue(this.userInfoMap, "secondMember" + "." + infoType);
	}

	public String getMemRenewalInfo(String infoType) {
		return getMapValue(this.userInfoMap, infoType);
	}
	
	public String getRenewalInfoAtAdd(String infoType) {
		return getMapValue(this.userInfoMap, "renewalInformation" + "." + infoType);
	}
	public String getRenewalInfoForScheduled(String infoType) {
		return getMapValue(this.userInfoMap, "renewalDetailsForSchedule" + "." + infoType);
	}
	
	public String getRenewalInfoForProcessing(String infoType) {
		return getMapValue(this.userInfoMap, "renewalDetailsForProcessing" + "." + infoType);
	}
	
	public String getRenewalInfoForSuccess(String infoType) {
		return getMapValue(this.userInfoMap, "renewalDetailsForSuccess" + "." + infoType);
	}
	
	public String getCreateRenewalInvoiceProcesingInfo(String infoType) {
		return getMapValue(this.userInfoMap, "renewalDetailsForProcessingCreateRenewalInvoice" + "." + infoType);
	}
	
	public String getCreateRenewalInvoiceSuccessInfo(String infoType) {
		return getMapValue(this.userInfoMap, "renewalDetailsForSuccessCreateRenewalInvoice" + "." + infoType);
	}
	public String getRenewalInvoiceDetails(String infoType) {
		return getMapValue(this.userInfoMap, "invoiceDetails" + "." + infoType);
	}
	
	public String getACSStoreInfo(String infoType) {
		return getMapValue(this.userInfoMap,infoType);
	}
	
	public String get_ACSStoreCaseId(String infoType) {
		return getMapValue(this.userInfoMap, infoType);
	}
	
	

}
