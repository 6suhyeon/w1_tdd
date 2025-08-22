package io.hhplus.tdd.point;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.bean.ChargeUserPointBean;
import io.hhplus.tdd.point.domain.UserPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class ChargeUserPointTest {

    @Mock
    private PointHistoryTable pointHistoryTable;

    @Mock
    private UserPointTable userPointTable;

    @InjectMocks
    private ChargeUserPointBean chargeUserPointBean;

    @Test
    @DisplayName("FAIL : 0원을 충전 시도했을 경우")
    void failWhenChargeZeroAmount() {
        // when & then: 0원 충전 시도 시 예외 발생
        assertThatThrownBy(() -> chargeUserPointBean.exec(1L, 0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("충전 금액이 0보다 커야 한다.");
    }

    @Test
    @DisplayName("포인트 충전 및 포인트 히스토리 기록")
    void chargeUserPointTest() {
        // given
        long userId = 1L;
        long chargePoint = 1000L;
        long existingPoint = 1500L;
        long exceptTotalPoint = 2500L;

        UserPoint currentUserPoint = new UserPoint(userId, existingPoint, System.currentTimeMillis());
        UserPoint updateUserPoint = new UserPoint(userId, exceptTotalPoint, System.currentTimeMillis());

        given(userPointTable.selectById(userId)).willReturn(currentUserPoint);
        given(userPointTable.insertOrUpdate(userId, exceptTotalPoint)).willReturn(updateUserPoint);

        // when
        UserPoint result = chargeUserPointBean.exec(userId, chargePoint);

        // then
        assertThat(result.point()).isEqualTo(exceptTotalPoint);
    }

}
