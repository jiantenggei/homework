package com.work.movie.controller;


import com.work.movie.service.MovieService;
import com.work.movie.vo.SearchVo;
import com.work.result.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jian
 * @since 2021-05-25
 */
@RestController
@RequestMapping("/movie")
public class MovieController {

    @Resource
    MovieService movieService;
    //分页查找 按中文名查找
    @PostMapping("/getMovies")
    public Result getMovies(@RequestBody SearchVo searchVo){
        return movieService.getMovies(searchVo);
    }
}
