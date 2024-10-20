package nextstep.qna.domain;

import nextstep.qna.domain.common.BaseTime;
import nextstep.qna.exception.CannotDeleteException;
import nextstep.qna.exception.NotFoundException;
import nextstep.qna.exception.UnAuthorizedException;
import nextstep.users.domain.NsUser;

public class Answer extends BaseTime {

    public static final String ANOTHER_OWNER_EXISTS_EXCEPTION_MESSAGE = "다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.";

    private Long id;

    private NsUser writer;

    private Question question;

    private String contents;

    private boolean deleted = false;

    public Answer() {
    }

    public Answer(NsUser writer, Question question, String contents) {
        this(null, writer, question, contents);
    }

    public Answer(Long id, NsUser writer, Question question, String contents) {
        this.id = id;
        if(writer == null) {
            throw new UnAuthorizedException();
        }

        if(question == null) {
            throw new NotFoundException();
        }

        this.writer = writer;
        this.question = question;
        this.contents = contents;
    }

    public Long getId() {
        return id;
    }

    public Answer setDeleted() {
        this.deleted = true;
        return this;
    }

    public boolean isDeleted() {
        return deleted;
    }
    public NsUser getWriter() {
        return writer;
    }

    public DeleteHistory createDeleteHistory() {
        return new DeleteHistory(ContentType.ANSWER, id, writer);
    }

    public void checkIfAnotherOwnerExists(NsUser loginUser) throws CannotDeleteException {
        if (isDifferentOwner(loginUser)) {
            throw new CannotDeleteException(ANOTHER_OWNER_EXISTS_EXCEPTION_MESSAGE);
        }
    }

    private boolean isDifferentOwner(NsUser writer) {
        return !this.writer.equals(writer);
    }

    @Override
    public String toString() {
        return "Answer [id=" + getId() + ", writer=" + writer + ", contents=" + contents + "]";
    }
}
