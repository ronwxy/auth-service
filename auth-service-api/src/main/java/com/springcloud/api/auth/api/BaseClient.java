package com.springcloud.api.auth.api;

import com.springboot.common.web.QueryResultDto;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


public interface BaseClient<ID extends Serializable, T, R extends Serializable> {
    String AUTH_SERVICE_NAME = "auth-service";

    @RequestMapping(method = RequestMethod.POST)
    R save(@RequestBody T t);

    @RequestMapping(method = RequestMethod.PUT)
    R update(@RequestBody T t);

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    R findById(@PathVariable("id") ID id);

    @RequestMapping(value = "batch", method = RequestMethod.GET)
    List<R> findByIds(@RequestParam("ids") List<ID> ids);

    @RequestMapping(method = RequestMethod.DELETE)
    void delete(@RequestBody T t);

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    void deleteById(@PathVariable("id") ID id);

    @RequestMapping(value = "batch", method = RequestMethod.DELETE)
    void deleteByIds(@RequestParam("ids") Collection<ID> ids);

    @RequestMapping(value = "paged", method = RequestMethod.GET)
    QueryResultDto<R> pagedList(@ModelAttribute T t,
                                @RequestParam(value = "page", defaultValue = "1") Integer page,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize);

    @RequestMapping(value = "list", method = RequestMethod.GET)
    List<R> list(@ModelAttribute T t);

    @RequestMapping(value = "one", method = RequestMethod.GET)
    R one(@ModelAttribute T t);
}
