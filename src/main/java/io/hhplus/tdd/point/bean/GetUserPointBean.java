package io.hhplus.tdd.point.bean;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.domain.UserPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetUserPointBean {
    private final UserPointTable userPointTable;

    @Autowired
    public GetUserPointBean(UserPointTable userPointTable) {
        this.userPointTable = userPointTable;
    }

    // 특정 유저의 포인트 조회
    public UserPoint exec(long id) {
        return userPointTable.selectById(id);
    }
}
