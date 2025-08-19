package io.hhplus.tdd.point.bean;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.domain.TransactionType;
import io.hhplus.tdd.point.domain.UserPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChargeUserPointBean {
    private final PointHistoryTable pointHistoryTable;
    private final UserPointTable userPointTable;

    @Autowired
    public ChargeUserPointBean(PointHistoryTable pointHistoryTable, UserPointTable userPointTable) {
        this.pointHistoryTable = pointHistoryTable;
        this.userPointTable = userPointTable;
    }

    // 특정 유저의 포인트 충전
    public UserPoint exec(long id, long amount) {
        // 유효성 검사
        if (amount <= 0) {
            throw new IllegalArgumentException("충전 금액이 0보다 커야 한다.");
        }

        // id를 통해 특정 유저 조회
        UserPoint userPoint = userPointTable.selectById(id);

        // 현재 포인트 + 추가된 포인트
        long updateAmount = userPoint.point() + amount;

        // 유저 포인트 업데이트
        UserPoint updateUserPoint = userPointTable.insertOrUpdate(id, updateAmount);

        // 히스토리 기록
        pointHistoryTable.insert(id, amount, TransactionType.CHARGE, System.currentTimeMillis());

        return updateUserPoint;
    }
}
