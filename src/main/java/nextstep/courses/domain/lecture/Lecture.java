package nextstep.courses.domain.lecture;

import nextstep.payments.domain.Payment;

public interface Lecture {

    default boolean isRecruitmentOpen(LectureStatus lectureStatus) {
        return LectureStatus.RECRUITING == lectureStatus;
    }

    void addRegistrationCount();

    boolean isRegistrationAvailable();

    boolean isPaymentAmountSameTuitionFee(Payment payment);
}
