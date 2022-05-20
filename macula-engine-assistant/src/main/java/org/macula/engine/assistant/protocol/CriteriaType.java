package org.macula.engine.assistant.protocol;

import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.macula.engine.assistant.query.QueryUtils;

import java.util.ResourceBundle;

/**
 * <p> <b>CriteriaType</b> 是查询方式类型 </p>
 */
public enum CriteriaType {

	// like '%x'
	StartWith {
		@Override
		public void collect(CriteriaVisitor collector, String column, Object value, Object anotherValue) {
			if (value instanceof String) {
				String paramName = collector.getTokenCreator().create(column);
				String paramValue = value + "%";
				collector.addQueryPart(column + " like :" + paramName);
				collector.addQueryParam(paramName, paramValue);
			}
		}

		@Override
		public boolean support(Class<?> claz) {
			return String.class.isAssignableFrom(claz);
		}

	},
	// like 'x%'
	EndWith {
		@Override
		public void collect(CriteriaVisitor collector, String column, Object value, Object anotherValue) {
			if (value instanceof String) {
				String paramName = collector.getTokenCreator().create(column);
				String paramValue = "%" + value;
				collector.addQueryPart(column + " like :" + paramName);
				collector.addQueryParam(paramName, paramValue);
			}
		}

		@Override
		public boolean support(Class<?> claz) {
			return String.class.isAssignableFrom(claz);
		}
	},
	// like '%x%'
	Contains {
		@Override
		public void collect(CriteriaVisitor collector, String column, Object value, Object anotherValue) {
			if (value instanceof String) {
				String paramName = collector.getTokenCreator().create(column);
				String paramValue = "%" + value + "%";
				collector.addQueryPart(column + " like :" + paramName);
				collector.addQueryParam(paramName, paramValue);
			}
		}

		@Override
		public boolean support(Class<?> claz) {
			return String.class.isAssignableFrom(claz);
		}
	},
	// not like '%x%'
	NotContains {
		@Override
		public void collect(CriteriaVisitor collector, String column, Object value, Object anotherValue) {
			if (value instanceof String) {
				String paramName = collector.getTokenCreator().create(column);
				String paramValue = "%" + value + "%";
				collector.addQueryPart(column + " not like :" + paramName);
				collector.addQueryParam(paramName, paramValue);
			}
		}

		@Override
		public boolean support(Class<?> claz) {
			return String.class.isAssignableFrom(claz);
		}
	},
	// = x
	Equals {
		@Override
		public void collect(CriteriaVisitor collector, String column, Object value, Object anotherValue) {
			if (value != null) {
				String paramName = collector.getTokenCreator().create(column);
				Object paramValue = value;
				collector.addQueryPart(column + " = :" + paramName);
				collector.addQueryParam(paramName, paramValue);
			}
		}

		@Override
		public boolean support(Class<?> claz) {
			return Object.class.isAssignableFrom(claz);
		}
	},
	// > x
	GreaterThan {
		@Override
		public void collect(CriteriaVisitor collector, String column, Object value, Object anotherValue) {
			if (value != null) {
				String paramName = collector.getTokenCreator().create(column);
				Object paramValue = value;
				collector.addQueryPart(column + " > :" + paramName);
				collector.addQueryParam(paramName, paramValue);
			}
		}

		@Override
		public boolean support(Class<?> claz) {
			return Number.class.isAssignableFrom(claz) || Date.class.isAssignableFrom(claz);
		}
	},
	// >= x
	GreaterOrEqual {
		@Override
		public void collect(CriteriaVisitor collector, String column, Object value, Object anotherValue) {
			if (value != null) {
				String paramName = collector.getTokenCreator().create(column);
				Object paramValue = value;
				collector.addQueryPart(column + " >= :" + paramName);
				collector.addQueryParam(paramName, paramValue);
			}
		}

		@Override
		public boolean support(Class<?> claz) {
			return Number.class.isAssignableFrom(claz) || Date.class.isAssignableFrom(claz);
		}
	},
	// < x
	LessThan {
		@Override
		public void collect(CriteriaVisitor collector, String column, Object value, Object anotherValue) {
			if (value != null) {
				String paramName = collector.getTokenCreator().create(column);
				Object paramValue = value;
				collector.addQueryPart(column + " < :" + paramName);
				collector.addQueryParam(paramName, paramValue);
			}
		}

		@Override
		public boolean support(Class<?> claz) {
			return Number.class.isAssignableFrom(claz);
		}
	},
	// <= x
	LessOrEqual {
		@Override
		public void collect(CriteriaVisitor collector, String column, Object value, Object anotherValue) {
			if (value != null) {
				String paramName = collector.getTokenCreator().create(column);
				Object paramValue = value;
				collector.addQueryPart(column + " <= :" + paramName);
				collector.addQueryParam(paramName, paramValue);
			}
		}

		@Override
		public boolean support(Class<?> claz) {
			return Number.class.isAssignableFrom(claz);
		}
	},
	// <> x
	NotEqual {
		@Override
		public void collect(CriteriaVisitor collector, String column, Object value, Object anotherValue) {
			if (value != null) {
				String paramName = collector.getTokenCreator().create(column);
				Object paramValue = value;
				collector.addQueryPart(column + " <> :" + paramName);
				collector.addQueryParam(paramName, paramValue);
			}
		}

		@Override
		public boolean support(Class<?> claz) {
			return Object.class.isAssignableFrom(claz);
		}
	},
	// < x 早于
	BeforeThan {
		@Override
		public void collect(CriteriaVisitor collector, String column, Object value, Object anotherValue) {
			if (value != null) {
				String paramName = collector.getTokenCreator().create(column);
				Object paramValue = value;
				collector.addQueryPart(column + " <= :" + paramName);
				collector.addQueryParam(paramName, paramValue);
			}
		}

		@Override
		public boolean support(Class<?> claz) {
			return Date.class.isAssignableFrom(claz);
		}
	},
	// > x 晚于
	AfterThan {
		@Override
		public void collect(CriteriaVisitor collector, String column, Object value, Object anotherValue) {
			if (value != null) {
				String paramName = collector.getTokenCreator().create(column);
				Object paramValue = value;
				collector.addQueryPart(column + " >= :" + paramName);
				collector.addQueryParam(paramName, paramValue);
			}
		}

		@Override
		public boolean support(Class<?> claz) {
			return Date.class.isAssignableFrom(claz);
		}
	},

	// >= x1 and < x2
	Between {
		@Override
		public void collect(CriteriaVisitor collector, String column, Object value, Object anotherValue) {
			if (value != null) {
				String paramName = collector.getTokenCreator().create(column);
				Object paramValue = value;
				collector.addQueryPart(column + " >= :" + paramName);
				collector.addQueryParam(paramName, paramValue);
			}
			if (anotherValue != null) {
				String paramName = collector.getTokenCreator().create(column);
				Object paramValue = anotherValue;
				collector.addQueryPart(column + " < :" + paramName);
				collector.addQueryParam(paramName, paramValue);
			}
		}

		@Override
		public boolean support(Class<?> claz) {
			return Number.class.isAssignableFrom(claz) || Date.class.isAssignableFrom(claz);
		}
	},
	Is {

		@Override
		public void collect(CriteriaVisitor collector, String column, Object value, Object anotherValue) {
			if (value == null) {
				collector.addQueryPart(column + " is null ");
			} else {
				collector.addQueryPart(column + " is not null ");
			}
		}

		@Override
		public boolean support(Class<?> claz) {
			return Boolean.class == claz;
		}

		@Override
		public boolean allowNull() {
			return true;
		}
	},
	// in ( x1, x2 )
	In {
		@Override
		public void collect(CriteriaVisitor collector, String column, Object value, Object anotherValue) {
			if (value instanceof Collection<?> && !((Collection<?>) value).isEmpty()) {
				StringBuilder sb = new StringBuilder();
				Map<String, ?> segements = QueryUtils.createQuerySegement(column, (Collection<?>) value, sb);
				collector.addQueryPart(sb.toString());
				for (Entry<String, ?> entry : segements.entrySet()) {
					collector.addQueryParam(entry.getKey(), entry.getValue());
				}
			}
		}

		@Override
		public boolean support(Class<?> claz) {
			return Number.class.isAssignableFrom(claz) || String.class.isAssignableFrom(claz);
		}
	};

	public abstract void collect(CriteriaVisitor collector, String column, Object value, Object anotherValue);

	public abstract boolean support(Class<?> claz);

	public boolean allowNull() {
		return false;
	}

	public String getLabel() {
		return getLabel(Locale.getDefault());
	}

	public String getLabel(Locale locale) {
		return ResourceBundle.getBundle(CriteriaType.class.getName(), locale).getString(this.toString());
	}

}
