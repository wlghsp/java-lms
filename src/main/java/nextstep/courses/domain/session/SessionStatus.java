package nextstep.courses.domain.session;

public enum SessionStatus {
    PREPARING, GATHERING, ON_GOING, END;

    public boolean isEnrollPossibleStatus() {
        return this == GATHERING;
    }
}
