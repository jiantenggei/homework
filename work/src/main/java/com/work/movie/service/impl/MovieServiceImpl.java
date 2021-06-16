package com.work.movie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.movie.entity.Movie;
import com.work.movie.mapper.MovieMapper;
import com.work.movie.service.MovieService;
import com.work.movie.vo.SearchVo;
import com.work.result.Result;
import com.work.result.ResultFactory;
import com.work.utils.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jian
 * @since 2021-05-25
 */
@Service
public class MovieServiceImpl extends ServiceImpl<MovieMapper, Movie> implements MovieService {

    @Resource
    private MovieMapper movieMapper;
    @Override
    public Result getMovies(SearchVo searchVo) {
        Integer pageSize = searchVo.getPageSize();
        Integer pageNo = searchVo.getPageNo();
        String movieName = searchVo.getMovieName();
        //先建立好分页
        IPage<Movie> movieIPage = new Page<>(pageNo, pageSize);
        //创建查询条件
       LambdaQueryWrapper<Movie> lambdaQueryWrapper = new LambdaQueryWrapper<>();
       lambdaQueryWrapper.like(!StringUtils.isEmpty(movieName),Movie::getMovieCname,movieName)
               .orderByDesc(Movie::getMovieScore);

       movieMapper.selectPage(movieIPage,lambdaQueryWrapper);
        PageUtil<Movie> pageUtil = new PageUtil<>(movieIPage);

        return ResultFactory.buildSuccessResult(pageUtil);
       //分页查询
    }
}
