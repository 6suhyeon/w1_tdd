package io.hhplus.tdd.point;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.point.bean.GetUserPointHistoryBean;
import io.hhplus.tdd.point.domain.PointHistory;
import io.hhplus.tdd.point.domain.TransactionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class GetUserPointHistoryTest {

    @Mock
    private PointHistoryTable pointHistoryTable;

    @InjectMocks
    private GetUserPointHistoryBean getUserPointHistoryBean;

    @Test
    @DisplayName("유저 id를 통해 유저 포인트 내역 조회(포인트 충전/사용)")
    void getUserPointHistoryTest() {
        // given
        long userId = 1L;
        List<PointHistory> exceptPointHistory = Arrays.asList(
                new PointHistory(1L, userId, 1000L, TransactionType.CHARGE, System.currentTimeMillis()),
                new PointHistory(2L, userId, 500L, TransactionType.USE, System.currentTimeMillis()),
                new PointHistory(3L, userId, 300L, TransactionType.CHARGE, System.currentTimeMillis())
                );
        given(pointHistoryTable.selectAllByUserId(userId)).willReturn(exceptPointHistory);

        // when
        List<PointHistory> result = getUserPointHistoryBean.exec(userId);

        // then
        assertThat(result).hasSize(3);
        assertThat(result.get(0).amount()).isEqualTo(1000L);
        assertThat(result.get(1).amount()).isEqualTo(500L);
        assertThat(result.get(2).amount()).isEqualTo(300L);
    }

    @Test
    @DisplayName("포인트 거래 내역이 없는 유저 조회")
    void getUserPointEmptyHistoryTest() {
        // given
        long userId = 1L;
        given(pointHistoryTable.selectAllByUserId(userId)).willReturn(Collections.emptyList());

        // when
        List<PointHistory> result = getUserPointHistoryBean.exec(userId);

        // then
        assertThat(result).isEmpty();
    }
}
