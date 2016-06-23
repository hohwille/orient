/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.impl.id;

import java.io.IOException;

import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import net.sf.mmm.orient.api.bean.OrientBean;
import net.sf.mmm.orient.api.mapping.OrientBeanMapper;
import net.sf.mmm.orient.base.id.OrientId;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.id.Id;

/**
 * An implementation of {@link JsonDeserializer} for {@link Id}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class OrientIdJsonDeserializer extends JsonDeserializer<OrientId<?>> {

  private OrientBeanMapper beanMapper;

  /**
   * The constructor.
   */
  public OrientIdJsonDeserializer() {
    super();
  }

  /**
   * The constructor.
   * 
   * @param beanMapper the {@link OrientBeanMapper}.
   */
  public OrientIdJsonDeserializer(OrientBeanMapper beanMapper) {
    super();
    this.beanMapper = beanMapper;
  }

  /**
   * @param beanMapper the {@link OrientBeanMapper} to {@link Inject}.
   */
  @Inject
  public void setBeanMapper(OrientBeanMapper beanMapper) {

    this.beanMapper = beanMapper;
  }

  @Override
  public OrientId<?> deserialize(JsonParser p, DeserializationContext ctx) throws IOException, JsonProcessingException {

    String idAsString = p.getValueAsString();
    OrientId<?> orientId = OrientId.of(null, idAsString);
    if (orientId != null) {
      OrientBean beanPrototype = this.beanMapper.getBeanPrototype(orientId.getOrid().getClusterId());
      if (beanPrototype != null) {
        Class<? extends Bean> beanClass = beanPrototype.access().getBeanClass();
        orientId = orientId.withType(beanClass);
      }
    }
    return orientId;
  }

}
