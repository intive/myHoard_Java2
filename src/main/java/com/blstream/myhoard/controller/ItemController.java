package com.blstream.myhoard.controller;

import com.blstream.myhoard.biz.exception.ErrorCode;
import com.blstream.myhoard.biz.exception.MyHoardException;
import com.blstream.myhoard.biz.model.ItemDTO;
import com.blstream.myhoard.biz.service.ResourceService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/items")
public class ItemController {

    private ResourceService<ItemDTO> itemService;

    public void setItemService(ResourceService<ItemDTO> itemService) {
        this.itemService = itemService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ItemDTO> getItems() {
        try {
            return itemService.getList();
        } catch (Exception ex) {
            throw new MyHoardException(320, "Nieznany błąd: " + ex.toString() + " > " + ex.getCause().toString());
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ItemDTO createItem(@Valid @RequestBody ItemDTO obj, BindingResult result) {
        if (result.hasErrors())
            throw new MyHoardException(320, result.getFieldError().getDefaultMessage());
        try {
            itemService.create(obj);
            return obj;
        } catch (Exception ex) {
            throw new MyHoardException(320, "Nieznany błąd: " + ex.toString() + " > " + ex.getCause().toString());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ItemDTO getItem(@PathVariable String id) {
        try {
            return itemService.get(Integer.parseInt(id));
        } catch (NumberFormatException ex) {
            throw new MyHoardException(320, "Niepoprawne id: " + id);
        } catch (RuntimeException ex) {
            throw new MyHoardException(320, "Nieznany błąd: " + ex.toString() + " > " + ex.getCause().toString());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ItemDTO updateItem(@PathVariable String id, @Valid @RequestBody ItemDTO obj, BindingResult result) {
        if (obj.getName() != null && result.hasFieldErrors("name") || obj.getCollection() != null && result.hasFieldErrors("collection"))
            throw new MyHoardException(320, result.getFieldError(obj.getName() != null ? "name" : "collection").getDefaultMessage());
        try {
            obj.setId(id);
            itemService.update(obj);
            return obj;
        } catch (Exception ex) {
            throw new MyHoardException(320, "Nieznany błąd: " + ex.toString() + " > " + ex.getCause().toString());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeItem(@PathVariable String id) {
        try {
            itemService.remove(Integer.parseInt(id));
        } catch (NumberFormatException ex) {
            throw new MyHoardException(320, "Niepoprawne id: " + id);
        } catch (RuntimeException ex) {
            throw new MyHoardException(320, "Nieznany błąd: " + ex.toString() + " > " + ex.getCause().toString());
        }
    }

    @ExceptionHandler(MyHoardException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorCode errorHandler(MyHoardException e) {
        return new ErrorCode(e.getErrorCode(), e.getErrorMsg());
    }
}
