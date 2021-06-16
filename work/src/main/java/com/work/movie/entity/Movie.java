package com.work.movie.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author jian
 * @since 2021-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 电影id
     */
    private String movieId;

    /**
     * 电影排名
     */
    private String movieTop;

    /**
     * 电影连接
     */
    private String movieUrl;

    /**
     * 图片连接
     */
    private String moviePicture;

    /**
     * 中文名
     */
    private String movieCname;

    /**
     * 外文名
     */
    private String movieFname;

    /**
     * 评价人数
     */
    private String movieComentNum;

    /**
     * 电影概况
     */
    private String movieAbstract;

    /**
     * 电影详情
     */
    private String movieDetail;

    private Float movieScore;


}
