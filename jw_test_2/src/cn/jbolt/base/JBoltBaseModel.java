package cn.jbolt.base;

import com.alibaba.druid.util.JdbcConstants;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.ActiveRecordException;
import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Table;
import cn.hutool.core.util.IdUtil;
import cn.jbolt.common.config.MainConfig;

/**
 * JBolt提供的BaseModel实现 主要用于处理一些底层逻辑覆盖
 * 注意：此文件通过JBolt生成器生成，请勿修改
 * @author jbolt.cn
 * @email 909854136@qq.com
 * @date 2020-12-15 21:46
 */
@SuppressWarnings("serial")
public abstract class JBoltBaseModel<M extends JBoltBaseModel<M>> extends Model<M> implements IBean{
	protected static final Log LOG = Log.getLog(JBoltBaseModel.class);
	/**
	 * 内置常量 可用于其他数据库标识boolean true
	 */
	public static final String TRUE="1";
	/**
	 * 内置常量 可用于其他数据库标识boolean false
	 */
	public static final String FALSE="0";
	/**
	 * 配置自动缓存处理
	 */
	private transient String idSequenceName;
	/**
	 * 映射表名 不参与序列化
	 */
	private transient String tableName;
	/**
	 * model 对应的数据库表 不参与序列化和持久化
	 */
	private transient Table table;
	/**
	 * 主键类型
	 */
	private transient Class<?> primaryType;
	/**
	 * 主键 不参与数据持久化和序列化
	 */
	private transient String[] primaryKeys;
	@Override
	public Boolean getBoolean(String attr) {
		Object value=_getAttrs().get(attr);
		if(value==null||value.toString().trim().length()==0) {return null;}
		//如果就是Boolean
		if(value instanceof Boolean) {
			return (Boolean)value;
		}
		//使用String 判断
		String valueStr=value.toString();
		int len=valueStr.length();
		//长度是1 并且值是1=true  0=false
		if(len==1) {
			if(valueStr.equals(TRUE)){
				return true;
			}else if(valueStr.equals(FALSE)){
				return false;
			}
		}else if(len==4){
			//长度是4 
			if(valueStr.equalsIgnoreCase("true")) {
				return true;
			}else if(valueStr.equalsIgnoreCase("false")) {
				return false;
			}
		}
		
		return (Boolean)value;
	}
	
	@Override
	public M set(String attr, Object value) {
		Table table = _getTable(); // table 为 null 时用于未启动 ActiveRecordPlugin 的场景
		if(table != null && !table.hasColumnLabel(attr)) {
			throw new ActiveRecordException("The attribute name does not exist: \"" + attr + "\"");
		}
		if(MainConfig.DB_TYPE.equals(JdbcConstants.MYSQL)==false) {
			if(value!=null) {
				if(value instanceof Boolean) {
					value = ((Boolean) value) ? TRUE : FALSE;
				} else if(value instanceof String) {
					String v = value.toString();
					int len = v.length();
					if(len == 4 && v.equalsIgnoreCase("true")) {
						value = TRUE;
					}else if(len == 5 && v.equalsIgnoreCase("false")) {
						value = FALSE;
					}
				}
			}
		}
		put(attr, value);
		_getModifyFlag().add(attr); // Add modify flag, update() need this flag.
		return (M) this;
	}
	
	/**
	 * 得到表名
	 * 
	 * @return
	 */
	public String _getTableName() {
		if (tableName == null) {
			tableName = _getTable(true).getName();
		}
		return tableName;
	}

	/**
	 * 得到映射表
	 */
	public Table _getTable() {
		return _getTable(false);
	}

	/**
	 * 获取Model映射数据库表
	 * 
	 * @param validateNull
	 * @return
	 */
	public Table _getTable(boolean validateNull) {
		if (table == null) {
			table = super._getTable();
			if (table == null && validateNull) {
				String msg = String.format(
						"class %s can not mapping to database table,maybe application cannot connect to database. ",
						_getUsefulClass().getName());
				LOG.error(msg);
				throw new RuntimeException(msg);
			}
		}
		return table;
	}
	
	/**
	 * 得到id序列名
	 * @return
	 */
	public String _getIdSequenceName() {
		if (idSequenceName == null) {
			idSequenceName = _getTableName()+"_id_seq";
		}
		return idSequenceName;
	}
	
	/**
	 * 获取主键名称
	 * 
	 * @return
	 */
	public String _getPrimaryKey() {
		return _getPrimaryKeys()[0];
	}

	/**
	 * 获取Model对应表的所有主键 大概率是一个
	 * 
	 * @return
	 */
	public String[] _getPrimaryKeys() {
		if (primaryKeys != null) {
			return primaryKeys;
		}
		primaryKeys = _getTable(true).getPrimaryKey();

		if (primaryKeys == null) {
			String msg = String.format("primaryKeys == null in [%s]", _getUsefulClass());
			LOG.error(msg);
			throw new RuntimeException(msg);
		}
		return primaryKeys;
	}
	/**
	 * 拼接序列值
	 * @return
	 */
	private String _sequenceNextId() {
		return _getIdSequenceName()+".nextval";
	}
	
	/**
	 * 初始化主键
	 */
	private void _initPrimaryKey() {
		//如果是NULL
		if(null == get(_getPrimaryKey())) {
			switch (MainConfig.ID_GEN_MODE) {
			case "snowflake":
				super.set(_getPrimaryKey(), IdUtil.getSnowflake(MainConfig.WORKER_ID, MainConfig.DATACENTER_ID).nextId());
				break;
			case "uuid":
				super.set(_getPrimaryKey(), IdUtil.fastSimpleUUID());
				break;
			case "sequence":
				super.set(_getPrimaryKey(), _sequenceNextId());
				break;
			}
		}
	}
	
	@Override
	public boolean save() {
		_initPrimaryKey();
		return this.superSave();
	}
	
	/**
	 * Model原始Save
	 * 
	 * @return
	 */
	protected boolean superSave() {
		return super.save();
	}
	
	
}