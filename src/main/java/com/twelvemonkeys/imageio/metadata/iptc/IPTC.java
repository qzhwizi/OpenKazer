package com.twelvemonkeys.imageio.metadata.iptc;

public interface IPTC {
   int ENVELOPE_RECORD = 256;
   int APPLICATION_RECORD = 512;
   int TAG_DESTINATION = 261;
   int TAG_PRODUCT_ID = 306;
   int TAG_CODED_CHARACTER_SET = 346;
   int TAG_RECORD_VERSION = 512;
   int TAG_OBJECT_TYPE_REFERENCE = 515;
   int TAG_OBJECT_ATTRIBUTE_REFERENCE = 516;
   int TAG_OBJECT_NAME = 517;
   int TAG_EDIT_STATUS = 519;
   int TAG_EDITORIAL_UPDATE = 520;
   int TAG_URGENCY = 522;
   int TAG_SUBJECT_REFERENCE = 524;
   int TAG_CATEGORY = 527;
   int TAG_SUPPLEMENTAL_CATEGORIES = 532;
   int TAG_FIXTURE_IDENTIFIER = 534;
   int TAG_KEYWORDS = 537;
   int TAG_CONTENT_LOCATION_CODE = 538;
   int TAG_CONTENT_LOCATION_NAME = 539;
   int TAG_RELEASE_DATE = 542;
   int TAG_RELEASE_TIME = 547;
   int TAG_EXPIRATION_DATE = 549;
   int TAG_EXPIRATION_TIME = 550;
   int TAG_SPECIAL_INSTRUCTIONS = 552;
   int TAG_ACTION_ADVICED = 554;
   int TAG_REFERENCE_SERVICE = 557;
   int TAG_REFERENCE_DATE = 559;
   int TAG_REFERENCE_NUMBER = 562;
   int TAG_DATE_CREATED = 567;
   int TAG_TIME_CREATED = 572;
   int TAG_DIGITAL_CREATION_DATE = 574;
   int TAG_DIGITAL_CREATION_TIME = 575;
   int TAG_ORIGINATING_PROGRAM = 577;
   int TAG_PROGRAM_VERSION = 582;
   int TAG_OBJECT_CYCLE = 587;
   int TAG_BY_LINE = 592;
   int TAG_BY_LINE_TITLE = 597;
   int TAG_CITY = 602;
   int TAG_SUB_LOCATION = 604;
   int TAG_PROVINCE_OR_STATE = 607;
   int TAG_COUNTRY_OR_PRIMARY_LOCATION_CODE = 612;
   int TAG_COUNTRY_OR_PRIMARY_LOCATION = 613;
   int TAG_ORIGINAL_TRANSMISSION_REFERENCE = 615;
   int TAG_HEADLINE = 617;
   int TAG_CREDIT = 622;
   int TAG_SOURCE = 627;
   int TAG_COPYRIGHT_NOTICE = 628;
   int TAG_CONTACT = 630;
   int TAG_CAPTION = 632;
   int TAG_WRITER = 634;
   int TAG_RASTERIZED_CATPTION = 637;
   int TAG_IMAGE_TYPE = 642;
   int TAG_IMAGE_ORIENTATION = 643;
   int TAG_LANGUAGE_IDENTIFIER = 647;
   int CUSTOM_TAG_JOBMINDER_ASSIGNMENT_DATA = 711;

   public static final class Tags {
      static boolean isArray(short var0) {
         switch (var0) {
            case 261:
            case 306:
            case 524:
            case 532:
            case 537:
            case 538:
            case 539:
            case 592:
               return true;
            default:
               return false;
         }
      }
   }
}
