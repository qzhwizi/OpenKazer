package com.twelvemonkeys.imageio.color;

import com.twelvemonkeys.lang.Validate;
import java.awt.color.ICC_Profile;

final class LCMSSanitizerStrategy implements ICCProfileSanitizer {
   @Override
   public void fixProfile(ICC_Profile var1) {
      Validate.notNull(var1, "profile");
   }

   @Override
   public boolean validationAltersProfileHeader() {
      return true;
   }
}
