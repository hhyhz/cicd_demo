package com.devops.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.devops.entity.SysSetting;
public interface ISysSettingService extends IService<SysSetting> {

	List<SysSetting> findAll();


}