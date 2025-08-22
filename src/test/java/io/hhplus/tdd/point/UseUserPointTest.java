package io.hhplus.tdd.point;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.bean.UseUserPointBean;
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
public class UseUserPointTest {

    @Mock
    private PointHistoryTable pointHistoryTable;

    @Mock
    private UserPointTable userPointTable;

    @InjectMocks
    private UseUserPointBean useUserPointBean;

    @Test
    @DisplayName("FAIL : 포인트 0원을 사용했을 경우")
    void failWhenUseZeroPoint() {
        // when & then: 0원 사용 시도 시 예외 발생
        assertThatThrownBy(() -> useUserPointBean.exec(1L, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("사용하려는 포인트가 0원 이상이어야 한다.");
    }

    @Test
    @DisplayName("FAIL : 가지고 있는 포인트보다 사용하려는 포인트가 더 많은 경우")
    void failWhenUseInsufficientPoint() {
        // given
        long userId = 1L;
        long currentPoints = 1000L;
        long useAmount = 1500L;

        given(userPointTable.selectById(userId))
                .willReturn(new UserPoint(userId, currentPoints, System.currentTimeMillis()));

        // when & then
        assertThatThrownBy(() -> useUserPointBean.exec(userId, useAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포인트가 부족합니다.");
    }

    @Test
    @DisplayName("포인트 사용 및 포인트 히스토리 기록")
    void useUserPointTest() {
        // given
        long userId = 1L;
        long currentPoints = 1500L;
        long useAmount = 1000L;
        long exceptRemaining = 500L;

        given(userPointTable.selectById(userId))
                .willReturn(new UserPoint(userId, currentPoints, System.currentTimeMillis()));
        given(userPointTable.insertOrUpdate(userId, exceptRemaining))
                .willReturn(new UserPoint(userId, exceptRemaining, System.currentTimeMillis()));

        // when
        UserPoint result = useUserPointBean.exec(userId, useAmount);

        // then
        assertThat(result.point()).isEqualTo(exceptRemaining);
    }
}
