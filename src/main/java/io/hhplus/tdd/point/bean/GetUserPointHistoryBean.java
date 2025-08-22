package io.hhplus.tdd.point.bean;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.point.domain.PointHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetUserPointHistoryBean {
    private final PointHistoryTable pointHistoryTable;

    @Autowired
    public GetUserPointHistoryBean(PointHistoryTable pointHistoryTable) {
        this.pointHistoryTable = pointHistoryTable;
    }

    // 특정 유저의 포인트 충전/이용 내역을 조회
    public List<PointHistory> exec(long id) {
        return pointHistoryTable.selectAllByUserId(id);
    }
}
