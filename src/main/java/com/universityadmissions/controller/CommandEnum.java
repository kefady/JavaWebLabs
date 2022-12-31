package com.universityadmissions.controller;

import com.universityadmissions.controller.command.*;
import com.universityadmissions.controller.command.admin.*;

public enum CommandEnum {
    REGISTRATION(new RegistrationCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    APPLY(new ApplyCommand()),
    SEND_APPLICATION(new SendApplicationCommand()),
    DELETE_USER_APPLICATION(new DeleteUserApplicationCommand()),
    PROFILE(new ProfileCommand()),
    CONSOLE(new ConsoleCommand()),
    CREATE_DEPARTMENT(new CreateDepartmentCommand()),
    DELETE_DEPARTMENT(new DeleteDepartmentCommand()),
    UPDATE_DEPARTMENT(new UpdateDepartmentCommand()),
    CREATE_EXAM(new CreateExamCommand()),
    BLOCK_USER(new BlockUserCommand()),
    UNBLOCK_USER(new UnblockUserCommand()),
    VERIFIED_APPLICATION(new VerifiedApplicationCommand()),
    START_ELECTION(new StartElectionCommand());

    private final Command command;

    CommandEnum(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
