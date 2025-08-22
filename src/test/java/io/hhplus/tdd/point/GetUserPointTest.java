package io.hhplus.tdd.point;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.bean.GetUserPointBean;
import io.hhplus.tdd.point.domain.UserPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class GetUserPointTest {

    @Mock
    private UserPointTable userPointTable;

    @InjectMocks
    private GetUserPointBean getUserPointBean;

    @Test
    @DisplayName("유저 id를 통해 포인트 조회 테스트")
    void getUserPointTest() {
        // given : 1500포인트를 보유한 사용자가 있을 때
        UserPoint exceptPoint = new UserPoint(1L, 1500, System.currentTimeMillis());
        given(userPointTable.selectById(1L)).willReturn(exceptPoint);

        // when : 포인트 조회 실행
        UserPoint result = getUserPointBean.exec(1L);

        // then : 조회된 포인트가 올바른지 판단
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.point()).isEqualTo(1500L);
        assertThat(result).isEqualTo(exceptPoint);
    }
}
