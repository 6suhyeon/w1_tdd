package io.hhplus.tdd.point.bean;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.domain.TransactionType;
import io.hhplus.tdd.point.domain.UserPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UseUserPointBean {
    private final UserPointTable userPointTable;
    private final PointHistoryTable pointHistoryTable;

    @Autowired
    public UseUserPointBean(UserPointTable userPointTable, PointHistoryTable pointHistoryTable) {
        this.userPointTable = userPointTable;
        this.pointHistoryTable = pointHistoryTable;
    }

    public UserPoint exec(long id, long amount) {
        // 유효성 검사
        if (amount <= 0) {
            throw new IllegalArgumentException("사용하려는 포인트가 0원 이상이어야 한다.");
        }

        // id를 통해 특정 유저 조회
        UserPoint userPoint = userPointTable.selectById(id);

        // 현재 가지고 있는 포인트가 사용하려는 포인트보다 많은지 확인
        if (userPoint.point() < amount) {
            throw new IllegalArgumentException("포인트가 부족합니다.");
        }

        // 현재 포인트 - 추가된 포인트
        long updateAmount = userPoint.point() - amount;

        // 유저 포인트 업데이트
        UserPoint updateUserPoint = userPointTable.insertOrUpdate(id, updateAmount);

        // 히스토리 기록
        pointHistoryTable.insert(id, amount, TransactionType.USE, System.currentTimeMillis());

        return updateUserPoint;
    }
}
