package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.qna.CannotConvertException;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AnswersTest {

    Answer A1_ANSWER_BY_JAVAJIGI_OF_Q1;
    Answer A2_ANSWER_BY_SANJIGI_OF_Q1;
    Answer A3_ANSWER_BY_SANJIGI_OF_Q1;
    Question Q1_QUESTION_BY_JAVAJIGI;

    @BeforeEach
    void setUp() {
        Q1_QUESTION_BY_JAVAJIGI = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        A1_ANSWER_BY_JAVAJIGI_OF_Q1 = new Answer(NsUserTest.JAVAJIGI, Q1_QUESTION_BY_JAVAJIGI, "Answers Contents1");
        A2_ANSWER_BY_SANJIGI_OF_Q1 = new Answer(NsUserTest.SANJIGI, Q1_QUESTION_BY_JAVAJIGI, "Answers Contents2");
        A3_ANSWER_BY_SANJIGI_OF_Q1 = new Answer(NsUserTest.SANJIGI, Q1_QUESTION_BY_JAVAJIGI, "Answers Contents3");
    }

    @DisplayName("삭제 요청 시 답변 리스트의 owner 중 하나라도 loginUser와 다르다면, CannotDeleteException를 던진다.")
    @Test
    void throwCannotDeleteExceptionNotAllMatchLoginUserAndOwner() {
        // given
        Answers answers = new Answers(List.of(A1_ANSWER_BY_JAVAJIGI_OF_Q1, A2_ANSWER_BY_SANJIGI_OF_Q1));

        // then
        assertThatThrownBy(() -> answers.delete(NsUserTest.JAVAJIGI))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessage("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }

    @DisplayName("답변 리스트의 owner와 loginUser가 모두 같다면, 리스트의 상태를 모두 삭제 상태로 바꾼다.")
    @Test
    void changeDeleteStatusIfOwnerAndLoginUserAllMatch() throws CannotDeleteException {
        // given
        Answers answers = new Answers(List.of(A2_ANSWER_BY_SANJIGI_OF_Q1, A3_ANSWER_BY_SANJIGI_OF_Q1));

        // when
        answers.delete(NsUserTest.SANJIGI);

        // then
        Assertions.assertThat(A2_ANSWER_BY_SANJIGI_OF_Q1.isDeleted()).isTrue();
        Assertions.assertThat(A3_ANSWER_BY_SANJIGI_OF_Q1.isDeleted()).isTrue();
    }

    @DisplayName("답변 리스트를 DeleteHistoryTargets로 반환받을 수 있다.")
    @Test
    void getAsDelteHistoryTargets() throws CannotDeleteException {
        // given
        Answers answers = new Answers(List.of(A2_ANSWER_BY_SANJIGI_OF_Q1, A3_ANSWER_BY_SANJIGI_OF_Q1));

        // when
        answers.delete(NsUserTest.SANJIGI);

        // then
        Assertions.assertThat(answers.asDeleteHistoryTargets().asList()).contains(
                new DeleteHistory(ContentType.ANSWER, A1_ANSWER_BY_JAVAJIGI_OF_Q1.getId(), NsUserTest.SANJIGI, LocalDateTime.now())
        );
    }

    @DisplayName("상태가 삭제가 아닌 요소가 존재할 경우, CannotConvertException")
    @Test
    void throwCannotTransportExceptionWhenNotAllDeletedStatus() {
        // given
        Answers answers = new Answers(List.of(A2_ANSWER_BY_SANJIGI_OF_Q1, A3_ANSWER_BY_SANJIGI_OF_Q1));

        // then
        Assertions.assertThatThrownBy(answers::asDeleteHistoryTargets)
                .isInstanceOf(CannotConvertException.class);
    }
}
