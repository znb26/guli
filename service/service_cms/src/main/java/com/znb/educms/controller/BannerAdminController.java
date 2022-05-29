package com.znb.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.znb.commonutils.R;
import com.znb.educms.entity.CrmBanner;
import com.znb.educms.service.ICrmBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 首页banner表 后台管理接口
 * </p>
 *
 * @author znb
 * @since 2022-05-29
 */
@RestController
@RequestMapping("/educms/banner")
@CrossOrigin
public class BannerAdminController {

    @Autowired
    private ICrmBannerService bannerService;

    /**
     * 分页查询banner
     */
    @GetMapping("/pageBanner/{page}/{limit}")
    @ApiOperation(value = "分页查询")
    public R pageBanner(@PathVariable long page,
                        @PathVariable long limit) {
        Page<CrmBanner> pageBanner = new Page<>(page,limit);
        bannerService.page(pageBanner, null);
        return R.ok().data("items",pageBanner.getRecords()).data("total",pageBanner.getTotal());
    }

    /**
     * 添加banner
     * @param crmBanner
     * @return
     */
    @PostMapping("/addBanner")
    @ApiOperation(value = "添加")
    public R addBanner(@RequestBody CrmBanner crmBanner) {
        bannerService.save(crmBanner);
        return R.ok();
    }

    /**
     * 根据id查询banner
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id查询banner")
    public R get(@PathVariable String id) {
        CrmBanner crmBanner = bannerService.getById(id);
        return R.ok().data("item",crmBanner);
    }

    /**
     * 修改banner
     * @param crmBanner
     * @return
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改")
    public R updateById(@RequestBody CrmBanner crmBanner) {
        bannerService.updateById(crmBanner);
        return R.ok();
    }

    /**
     * 删除banner
     * @param id bannerId
     * @return
     */
    @DeleteMapping("/remove/{id}")
    @ApiOperation(value = "删除")
    public R remove(@PathVariable String id) {
        bannerService.removeById(id);
        return R.ok();
    }

}
