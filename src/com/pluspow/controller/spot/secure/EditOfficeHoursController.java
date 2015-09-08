package com.pluspow.controller.spot.secure;

import org.slim3.controller.Navigation;

import com.pluspow.enums.DayOfWeek;
import com.pluspow.model.Client;
import com.pluspow.model.OfficeHours;
import com.pluspow.model.Spot;
import com.pluspow.service.OfficeHoursService;

public class EditOfficeHoursController extends BaseController {

    @Override
    protected Navigation execute(Client client, Spot spot) throws Exception {
        
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(asString("dayOfWeek"));
        
        OfficeHours officeHours = OfficeHoursService.getOfficeHour(spot, dayOfWeek);
        
        requestScope("officeHours", officeHours);
        
        return forward("editOfficeHours.jsp");
    }
}
