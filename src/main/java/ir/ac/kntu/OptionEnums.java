package ir.ac.kntu;

public class OptionEnums {
    public enum UserMainMenuOption {
        ACCOUNT_MANAGEMENT,
        CONTACTS,
        TRANSFER_MONEY,
        CHARGE_SIM,
        SUPPORT,
        SETTINGS,
        ACCOUNT_DETAILS,
        LOG_OUT,
        QUIT;
    }

    public enum ChargeSimOptions{
        YOURSELF,
        CONTACTS,
        PHONE_NUMBER,
        RETURN;
    }

    public enum SupportMenuOption {
        NEW_TICKET,
        TICKETS,
        RETURN;
    }

    public enum SettingsMenuOption {
        CHANGE_PASSWORD,
        CHANGE_CREDIT_CARD_PASSWORD,
        ACTIVATE_DEACTIVATE_CONTACTS,
        RETURN;
    }

    public enum TransferMenuOption {
        ACCOUNT_ID,
        CONTACTS,
        RECENT_USERS,
        RETURN;
    }

    public enum ContactsMenuOption {
        ADD_CONTACT,
        VIEW_CONTACTS,
        RETURN;
    }

    public enum ManagementMenuOption {
        CHARGE,
        VIEW_ACCOUNT_BALANCE,
        VIEW_SIM_CARD_BALANCE,
        VIEW_RECEIPTS,
        RETURN;
    }

    public enum ShowReceiptsOption {
        VIEW_ALL,
        FILTER_BY_TIME_SPAN,
        RETURN;
    }

    public enum SelectRuleOption {
        USER,
        ADMIN,
        QUIT;
    }

    public enum AdminMenu {
        AUTHENTICATION_REQUESTS,
        TICKETS,
        USERS,
        LOG_OUT;
    }

    public enum AdminUserMenu {
        VIEW_ALL,
        SEARCH,
        RETURN;
    }

    public enum SignOrLogin {
        LOGIN,
        SIGN_UP,
        RETURN;
    }
}

