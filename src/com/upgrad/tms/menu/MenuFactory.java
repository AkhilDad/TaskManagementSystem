package com.upgrad.tms.menu;

import com.upgrad.tms.exception.NotFoundException;

import java.io.IOException;

/**
 * This class will return options menu concrete implementation based on option menu type.
 */
public class MenuFactory {

    public static OptionsMenu getMenuByType(OptionsMenuType optionsMenuType) throws IOException, ClassNotFoundException {
        switch (optionsMenuType){
            case PROJECT_MANAGER:
                return new ManagerMenu();
            case ASSIGNEE:
                return new AssigneeMenu();
        }
        //here no return value we have, so we can't do anything, rather throw an exception
        throw new NotFoundException("This OptionsMenuType not supported: "+ optionsMenuType);
    }
}
