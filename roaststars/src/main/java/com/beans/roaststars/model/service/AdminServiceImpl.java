package com.beans.roaststars.model.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.beans.roaststars.model.mapper.AdminMapper;
import com.beans.roaststars.model.vo.AdminListVO;

@Service
public class AdminServiceImpl implements AdminService{
	@Resource
	private AdminMapper adminMapper;

	// 등급업 대기인원 리스트(페이징 적용)
    // findReviewListByCafeNo(String cafeNo)
    // : 페이지 번호가 없을 때는 default 1 page
    @Override
    public AdminListVO getAllWatingForAuthor() {
       return getAllWatingForAuthor("1");
    }
    
    @Override
    public AdminListVO getAllWatingForAuthor(String pageNo) {
       //카페에 해당하는 전체 리뷰 수
       int memberTotalCount = adminMapper.getTotalCountByWaitingMember();

       //페이징 빈 생성
       PagingBean pagingBean = null;
       
       if (pageNo == null)
          pagingBean = new PagingBean(memberTotalCount);
       else
          pagingBean = new PagingBean(memberTotalCount, Integer.parseInt(pageNo));
       
       // 페이지 그룹 당 페이지 수
	   pagingBean.setPageNumberPerPageGroup(4);
	   //페이지 당 게시물 수
	   pagingBean.setContentNumberPerPage(5);
       
       AdminListVO listVO 
          = new AdminListVO(adminMapper.getAllWatingForAuthor(pagingBean), pagingBean);

       return listVO;
    }
   
	//회원 권한 종류 받아오기
	@Override
	public List<String> getUserAuthorityList(){
		return adminMapper.getUserAuthorityList();
	}
	//대기중인 회원 총 인원
	@Override
	public int getTotalCountByWaitingMember() {
		return adminMapper.getTotalCountByWaitingMember();
	}
	//권한 부여
	@Override
	public int grantAuthority(String id, String authority) {
		return adminMapper.grantAuthority(id, authority);
	}

}
