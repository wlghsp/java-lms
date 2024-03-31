package nextstep.qna.domain;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerTest {
    Answer A1_ANSWER_BY_JAVAJIGI_OF_Q1;
    Answer A2_ANSWER_BY_SANJIGI_OF_Q1;
    Question Q1_QUESTION_BY_JAVAJIGI;

    @BeforeEach
    void setUp() {
        Q1_QUESTION_BY_JAVAJIGI = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        A1_ANSWER_BY_JAVAJIGI_OF_Q1 = new Answer(NsUserTest.JAVAJIGI, Q1_QUESTION_BY_JAVAJIGI, "Answers Contents1");
        A2_ANSWER_BY_SANJIGI_OF_Q1 = new Answer(NsUserTest.SANJIGI, Q1_QUESTION_BY_JAVAJIGI, "Answers Contents2");

    }

    @DisplayName("Answer에 대한 삭제를 진행할 때, loginUser와 writer가 같다면 삭제 상태를 변경한다.")
    @Test
    void changeDeleteStatueWhenLoginUserAndWriterSame() {
        // when
        A1_ANSWER_BY_JAVAJIGI_OF_Q1.delete();

        // then
        assertThat(A1_ANSWER_BY_JAVAJIGI_OF_Q1.isDeleted()).isTrue();
    }

    @DisplayName("생성한 Answer에 대해, DeleteHistory로 변환하여 받을 수 있다.")
    @Test
    void getAsDeleteHistory() {
        // given
        DeleteHistory deleteHistory =
                new DeleteHistory(ContentType.ANSWER, A1_ANSWER_BY_JAVAJIGI_OF_Q1.getId(), NsUserTest.JAVAJIGI, LocalDateTime.now());

        // then
        assertThat(A1_ANSWER_BY_JAVAJIGI_OF_Q1.asDeleteHistory()).isEqualTo(deleteHistory);
    }
}
