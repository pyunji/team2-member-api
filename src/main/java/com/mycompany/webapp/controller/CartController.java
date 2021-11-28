package com.mycompany.webapp.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.Color;
import com.mycompany.webapp.dto.Product;
import com.mycompany.webapp.dto.ProductToCart;
import com.mycompany.webapp.dto.Size;
import com.mycompany.webapp.service.CartService;
import com.mycompany.webapp.vo.Cart;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	private static final Logger logger = LoggerFactory.getLogger(CartController.class);

	@Resource private CartService cartService;
	
	@GetMapping("")
	public List<Product> cartList(@RequestBody Map<String, String> map) {
		logger.info("실행");
//		String mid = principal.getName();
		logger.info("auth = {}", map.get("auth"));
		List<Product> cartItems = cartService.getList(map.get("auth"));
//		model.addAttribute("cartItems", cartItems);
//		model.addAttribute("cartSize", cartItems.size());
		
		return cartItems;
	}
	
	/* author : 채연
	 * 상품상세페이지에서 장바구니로 데이터 insert 넘기기
	 * 같은 상품 담길 시 수량 증가 */
	@PostMapping("")
	public void insertCart(@RequestBody ProductToCart product) {
		//장바구니에 상품 담기
		String pStockId = product.getProductStockId();
		Cart cart = new Cart();
		cart.setMemberId(product.getAuth());
		cart.setProductStockId(pStockId);
		cart.setQuantity(product.getQuantity());
		
		int cartItem = cartService.insertCart(cart);
		
//		return "redirect:/cart";
	}
	
	/*
	 * 카트 테이블에서 상품 수량을 갱신
	 */
	@PostMapping("/update/quantity")
	public void updateQuantity(@RequestBody Map<String, String> map) {
		cartService.updateQuantity(Integer.parseInt(map.get("quantity")), map.get("pstockid"), map.get("auth"));
//		return "redirect:/cart";
	}
	
	@PostMapping("/update/options")
	public void updateOptions(@RequestBody ProductToCart product) {
		cartService.updateOptions(product, product.getAuth());
//		return "redirect:/cart";
	}
	
	@PostMapping("/selectColors")
	public List<Color> selectColors(@RequestBody String pcommonId){
		logger.info("실행");
		List<Color> colors = cartService.getColors(pcommonId);
//		model.addAttribute("colors", colors);
		return colors;
	}
	
	@RequestMapping("/selectSizesByPcolorId")
	public List<Size> selectSizesPcolorId(@RequestBody String pcolorId) {
		logger.info("실행");
		List<Size> sizes = cartService.getSizesByPcolorId(pcolorId);
//		model.addAttribute("sizes", sizes);
		return sizes;
	}

	
	@PostMapping("/delete")
	public void deleteCartItem(@RequestBody Map<String, String> map) {
//		String mid = principal.getName();
		String pstockId = map.get("pcolorid") + "_" + map.get("sizecode");
		Cart cart = new Cart();
		cart.setMemberId(map.get("auth"));
		cart.setProductStockId(pstockId);
		List<Cart> carts = new ArrayList<Cart>();
		carts.add(cart);
		cartService.deleteCart(carts);
		logger.info("pcolorId: "+ map.get("pcolorid"));
		logger.info("sizeCode: "+  map.get("sizecode"));
//		return "redirect:/cart";
	}
	
	/*보류*/
	@PostMapping("/deleteSelected")
	public void deleteSelected(@RequestBody String productStockIds, Principal principal) {
		String mid = principal.getName();
		logger.info("실행");
		logger.info("productStockIds = " + productStockIds);
		
		JSONObject jsonObject = new JSONObject(productStockIds);
		JSONArray jsonArray = jsonObject.getJSONArray("productStockIds");
		List<Cart> delItems = new ArrayList<Cart>();
		for(int i = 0; i < jsonArray.length(); i++) {
			Cart cart = new Cart();
			cart.setMemberId(mid);
			cart.setProductStockId(jsonArray.getString(i));
			delItems.add(cart);
		}
		cartService.deleteCart(delItems);
		logger.info(jsonArray.getString(0));
		
		logger.info("type = " + jsonObject);
//		return "redirect:/cart";
	}
}
