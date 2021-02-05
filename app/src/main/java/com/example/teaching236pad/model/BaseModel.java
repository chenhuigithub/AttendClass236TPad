package com.example.teaching236pad.model;

import java.io.Serializable;

/**
 * 基础模型类,暂保留,将来可能添加公共方法
 * 
 * @author zhaochenhui
 * 
 */
public abstract class BaseModel implements Serializable {

	/**
	 * 随机生成的uid
	 */
	private static final long serialVersionUID = -4556164228618659977L;

	/**
	 * key-value对应接口，方便取值
	 * 
	 * @author zhaochenhui
	 * 
	 */
	public interface ICanGetKeyValue {
		public abstract String getCustomCode();

		public abstract String getCustomName();
	}

	/**
	 * ICanGetKeyValue的升级版，添加了传递图片的功能
	 * 
	 * @author Administrator
	 * 
	 */
	public interface ICanGetKeyValueWithPic extends ICanGetKeyValue {
		/**
		 * 获取图片-bitmap格式
		 * 
		 * @return
		 */
		public abstract byte[] getPicByte();

		/**
		 * 获取图片路径
		 * 
		 * @return
		 */
		public abstract String getPicPath();
	}
}
