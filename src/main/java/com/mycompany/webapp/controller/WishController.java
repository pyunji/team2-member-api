package com.mycompany.webapp.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.CartItem;
import com.mycompany.webapp.dto.WishItem;
import com.mycompany.webapp.security.JwtUtil;
import com.mycompany.webapp.service.WishService;
import com.mycompany.webapp.vo.Wish;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/wish")
@Slf4j
public class WishController {	
	@Resource private WishService wishService;
	
	/* 관심상품 조회*/
	@GetMapping("")
	public List<WishItem> wishList(HttpServletRequest request) {
		String mid = JwtUtil.getMidFromRequest(request);
		List<WishItem> wishlist= wishService.getList(mid);
		log.info("cartItems = {}", wishlist);
		return wishlist;
	}
	
	/* 관심상품 추가 */
	@PostMapping("")
	public void insertWish(HttpServletRequest request, @RequestBody Wish wish) {
		String mid = JwtUtil.getMidFromRequest(request);
		wish.setMid(mid);
		
//		//test
//		Wish wish1 = new Wish();
//		wish1.setMid("user1");
//		wish1.setPstockid(wish.getPstockid());
		
		wishService.insertItem(wish);
	}
	
	/* 관심상품 삭제 */
	@DeleteMapping("/{pstockid}")
	public void deleteWish(HttpServletRequest request, @PathVariable String pstockid) {
		String mid = JwtUtil.getMidFromRequest(request);
		Wish wish = new Wish();
		wish.setMid(mid);
		wish.setPstockid(pstockid);
		
		wishService.deleteItem(wish);
	}
	
	/* 관심상품에 이미 있는지 여부 확인하기 */
	@GetMapping("/{pstockid}")
	public int selectCount(HttpServletRequest request, @PathVariable String pstockid) {
		String mid = JwtUtil.getMidFromRequest(request);
		Wish wish = new Wish();
		wish.setMid(mid);
		wish.setPstockid(pstockid);
		
		return wishService.selectCount(wish);
	}
}
