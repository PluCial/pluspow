package com.pluspow.controller.client;

import org.slim3.controller.Navigation;
import org.slim3.util.StringUtil;

import com.pluspow.enums.EntryType;
import com.pluspow.enums.Lang;
import com.pluspow.exception.NoContentsException;
import com.pluspow.model.Entry;
import com.pluspow.service.ResetPasswordEntryService;
import com.pluspow.service.SignupEntryService;

public class EntryCompleteController extends BaseController {

    @Override
    protected Navigation execute(Lang lang) throws Exception {
        String entryId = asString("entryId");
        String type = asString("type");
        
        if(StringUtil.isEmpty(entryId) || StringUtil.isEmpty(type)) {
            throw new NoContentsException();
        }
        
        // エントリータイプの設定
        EntryType entryType = null;
        try {
            entryType = EntryType.valueOf(type);
            
        }catch(Exception e) {
            throw new NoContentsException();
        }
        
        Entry entry = null;
        
        
        // DBからエントリー情報を取得
        if(entryType == EntryType.REGISTER) {
            entry = SignupEntryService.getByKey(entryId);

        }else if(entryType == EntryType.RESET_PASSWORD) {
            entry = ResetPasswordEntryService.getByKey(entryId);
            
        }else {
            throw new NoContentsException();
        }
        
        if(entry == null) throw new NoContentsException();
        
        return redirect(entryType.getRedirectPath() + "?entryId=" + entryId);
    }
}
