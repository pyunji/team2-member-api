package com.mycompany.webapp.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.Color;
import com.mycompany.webapp.dto.DelItemsByOrder;
import com.mycompany.webapp.dto.ProductToCart;
import com.mycompany.webapp.dto.Size;
import com.mycompany.webapp.security.JwtUtil;
import com.mycompany.webapp.service.CartService;
import com.mycompany.webapp.vo.Cart;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/cart")
@Slf4j
public class CartController {
	private static String mid;
	@Resource private CartService cartService;
	
	@GetMapping("")
	public List<Map> cartList(HttpServletRequest request) {
		mid = JwtUtil.getMidFromRequest(request);
		
//		List<Product> cartItems = cartService.getList(mid);
		List<Map> cartItems = cartService.getList(mid);
		log.info("cartItems = {}", cartItems);
		return cartItems;
	}
	
	/* 상품상세페이지에서 장바구니로 데이터 insert 넘기기
	 * 같은 상품 담길 시 수량 증가 */
	@PostMapping("")
	public void insertCart(@RequestBody ProductToCart product) {
		//장바구니에 상품 담기
		String pStockId = product.getProductStockId();
		Cart cart = new Cart();
		cart.setMid(product.getAuth());
		cart.setPstockid(pStockId);
		cart.setQuantity(product.getQuantity());
		
		int cartItem = cartService.insertCart(cart);
		
//		return "redirect:/cart";
	}
	
	/*
	 * 카트 테이블에서 상품 수량을 갱신
	 */
	@PostMapping("/update/quantity")
	public void updateQuantity(HttpServletRequest request, @RequestBody Map<String, String> map) {
		mid = JwtUtil.getMidFromRequest(request);
		cartService.updateQuantity(Integer.parseInt(map.get("quantity")), map.get("pstockid"), mid);
//		return "redirect:/cart";
	}
	
	@PostMapping("/update/options")
	public void updateOptions(@RequestBody ProductToCart product) {
		cartService.updateOptions(product, product.getAuth());
//		return "redirect:/cart";
	}
	
	@PostMapping("/selectColors")
	public List<Color> selectColors(@RequestBody String pcommonId){
		log.info("실행");
		List<Color> colors = cartService.getColors(pcommonId);
//		model.addAttribute("colors", colors);
		return colors;
	}
	
	@RequestMapping("/selectSizesByPcolorId")
	public List<Size> selectSizesPcolorId(@RequestBody String pcolorId) {
		log.info("실행");
		List<Size> sizes = cartService.getSizesByPcolorId(pcolorId);
//		model.addAttribute("sizes", sizes);
		return sizes;
	}

	/* 하나의 상품 삭제 */
	@PostMapping("/delete")
	public void deleteCartItem(@RequestBody String pstockid) {
//		String mid = principal.getName();
//		String pstockId = map.get("pcolorid") + "_" + map.get("sizecode");
		log.info(pstockid);
		
		Cart cart = new Cart();
		cart.setMid(mid);
		cart.setPstockid(pstockid);
		List<Cart> carts = new ArrayList<Cart>();
		carts.add(cart);
		cartService.deleteCart(carts);

//		log.info("pcolorId: "+ map.get("pcolorid"));
//		log.info("sizeCode: "+  map.get("sizecode"));
//		return "redirect:/cart";
	}
	
	/* 여러 상품 삭제 */
	@PostMapping("/deleteSelected")
	public void deleteSelected(HttpServletRequest request, @RequestBody List<String> delIds) {
		mid = JwtUtil.getMidFromRequest(request);
		List<Cart> delItems = new ArrayList<>();
		for(String delId: delIds) {
			Cart cart = new Cart();
			cart.setMid(mid);
			cart.setPstockid(delId);
			delItems.add(cart);
		}
		
		cartService.deleteCart(delItems);
//		log.info(jsonArray.getString(0));
//		
//		log.info("type = " + jsonObject);
//		return "redirect:/cart";
	}
	
	@PostMapping("/deletebyorder")
	public void deleteByOrder(HttpServletRequest request, @RequestBody List<String> delItemsByOrder) {
		log.info("실행");
		mid = JwtUtil.getMidFromRequest(request);
		log.info("mid = {}", mid);
		List<Cart> carts = new ArrayList<Cart>();
		for(String delItemByOrder : delItemsByOrder) {
			Cart cart = new Cart();
			cart.setMid(mid);
			cart.setPstockid(delItemByOrder);
			carts.add(cart);
		}
		cartService.deleteCart(carts);
	}
}
