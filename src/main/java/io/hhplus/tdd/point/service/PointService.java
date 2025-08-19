package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.domain.PointHistory;
import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.bean.ChargeUserPointBean;
import io.hhplus.tdd.point.bean.GetUserPointBean;
import io.hhplus.tdd.point.bean.GetUserPointHistoryBean;
import io.hhplus.tdd.point.bean.UseUserPointBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointService {
    private final GetUserPointBean getUserPointBean;
    private final GetUserPointHistoryBean getUserPointHistoryBean;
    private final ChargeUserPointBean chargeUserPointBean;
    private final UseUserPointBean useUserPointBean;

    @Autowired
    public PointService(GetUserPointBean getUserPointBean, GetUserPointHistoryBean getUserPointHistoryBean, ChargeUserPointBean chargeUserPointBean, UseUserPointBean useUserPointBean) {
        this.getUserPointBean = getUserPointBean;
        this.getUserPointHistoryBean = getUserPointHistoryBean;
        this.chargeUserPointBean = chargeUserPointBean;
        this.useUserPointBean = useUserPointBean;
    }

    // 특정 유저의 포인트 조회
    public UserPoint getUserPoint(long id) {
        return getUserPointBean.exec(id);
    }

    // 특정 유저의 포인트 충전/이용 내역을 조회
    public List<PointHistory> getUserPointHistory(long id) {
        return getUserPointHistoryBean.exec(id);
    }

    // 특정 유저의 포인트 충전
    public UserPoint chargeUserPoint(long id, long amount) {
        return chargeUserPointBean.exec(id, amount);
    }

    // 특정 유저의 포인트를 사용
    public UserPoint useUserPoint(long id, long amount) {
        return useUserPointBean.exec(id, amount);
    }
}
