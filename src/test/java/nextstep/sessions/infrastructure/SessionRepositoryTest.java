package nextstep.sessions.infrastructure;

import nextstep.sessions.domain.Session;
import nextstep.sessions.domain.SessionBuilder;
import nextstep.sessions.domain.SessionCoverImage;
import nextstep.sessions.domain.SessionDuration;
import nextstep.sessions.domain.SessionPaymentType;
import nextstep.sessions.domain.SessionRecruitmentStatus;
import nextstep.sessions.domain.SessionRegistrationBuilder;
import nextstep.sessions.domain.SessionRepository;
import nextstep.sessions.domain.SessionStatus;
import nextstep.students.domain.Students;
import nextstep.students.infrastructure.JdbcStudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
class SessionRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(SessionRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private SessionRepository sessionRepository;
    private SessionDuration durationMock;
    private SessionCoverImage coverImageMock;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate, new JdbcStudentRepository(jdbcTemplate));
        durationMock = new SessionDuration(
                LocalDateTime.of(2023, 4, 3, 0, 0),
                LocalDateTime.of(2023, 6, 1, 0, 0)
        );
        coverImageMock = SessionCoverImage.create("http://test.com/image1");
    }

    @Test
    @DisplayName("Session 정보를 테이블에 저장하고 조회합니다.")
    void crud() {
        long testID = 1L;
        Session session = SessionBuilder.aSession()
                .withId(testID)
                .withCourseId(1L)
                .withDuration(durationMock)
                .withCoverImage(coverImageMock)
                .withPaymentType(SessionPaymentType.PAID)
                .with(SessionRegistrationBuilder.aRegistration()
                        .withStatus(SessionStatus.PREPARING)
                        .withRecruitmentStatus(SessionRecruitmentStatus.RECRUITING)
                        .withStudents(new Students())
                        .withStudentCapacity(5))
                .withCreatedAt(LocalDateTime.now())
                .build();

        log.debug("SESSION SAVE: {}", session);
        int count = sessionRepository.save(session);

        Session savedSession = sessionRepository.findById(testID).get();
        log.debug("SESSION READ: {}", savedSession);

        assertAll(
                () -> assertThat(count).isOne(),
                () -> assertThat(savedSession.getDuration()).isEqualTo(durationMock),
                () -> assertThat(savedSession.getCoverImage()).isEqualTo(coverImageMock),
                () -> assertThat(savedSession.getPaymentType()).isEqualTo(SessionPaymentType.PAID),
                () -> assertThat(savedSession.getRegistration())
                        .isEqualTo(SessionRegistrationBuilder.aRegistration()
                                .withStatus(SessionStatus.PREPARING)
                                .withRecruitmentStatus(SessionRecruitmentStatus.RECRUITING)
                                .withStudents(new Students())
                                .withStudentCapacity(5).build())
        );
    }

}
