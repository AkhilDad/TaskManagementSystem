package com.upgrad.tms.menu;

import com.upgrad.tms.entities.User;
import com.upgrad.tms.entities.UserRole;
import com.upgrad.tms.exception.NotFoundException;

import java.io.IOException;

/**
 * This class will return options menu concrete implementation based on user role.
 */
public class UserMenuFactory {

    public static OptionsMenu getUserSpecificMenu(UserRole userRole) throws IOException, ClassNotFoundException {
        switch (userRole){
            case MANAGER:
                return new ManagerMenu();
            case SUB_ORDINATE:
                return new SubOrdinateMenu();
        }
        throw new NotFoundException("Menu for user role not found");
    }
}
