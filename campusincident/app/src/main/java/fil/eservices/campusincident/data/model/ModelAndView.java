/**
 * Campus Incident API Documentation
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: 0.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package fil.eservices.campusincident.data.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

@ApiModel(description = "")
public class ModelAndView {
  
  @SerializedName("empty")
  private Boolean empty = null;
  @SerializedName("model")
  private Object model = null;
  @SerializedName("modelMap")
  private Map<String, Object> modelMap = null;
  @SerializedName("reference")
  private Boolean reference = null;
  public enum StatusEnum {
     CONTINUE,  SWITCHING_PROTOCOLS,  PROCESSING,  CHECKPOINT,  OK,  CREATED,  ACCEPTED,  NON_AUTHORITATIVE_INFORMATION,  NO_CONTENT,  RESET_CONTENT,  PARTIAL_CONTENT,  MULTI_STATUS,  ALREADY_REPORTED,  IM_USED,  MULTIPLE_CHOICES,  MOVED_PERMANENTLY,  FOUND,  MOVED_TEMPORARILY,  SEE_OTHER,  NOT_MODIFIED,  USE_PROXY,  TEMPORARY_REDIRECT,  PERMANENT_REDIRECT,  BAD_REQUEST,  UNAUTHORIZED,  PAYMENT_REQUIRED,  FORBIDDEN,  NOT_FOUND,  METHOD_NOT_ALLOWED,  NOT_ACCEPTABLE,  PROXY_AUTHENTICATION_REQUIRED,  REQUEST_TIMEOUT,  CONFLICT,  GONE,  LENGTH_REQUIRED,  PRECONDITION_FAILED,  PAYLOAD_TOO_LARGE,  REQUEST_ENTITY_TOO_LARGE,  URI_TOO_LONG,  REQUEST_URI_TOO_LONG,  UNSUPPORTED_MEDIA_TYPE,  REQUESTED_RANGE_NOT_SATISFIABLE,  EXPECTATION_FAILED,  I_AM_A_TEAPOT,  INSUFFICIENT_SPACE_ON_RESOURCE,  METHOD_FAILURE,  DESTINATION_LOCKED,  UNPROCESSABLE_ENTITY,  LOCKED,  FAILED_DEPENDENCY,  TOO_EARLY,  UPGRADE_REQUIRED,  PRECONDITION_REQUIRED,  TOO_MANY_REQUESTS,  REQUEST_HEADER_FIELDS_TOO_LARGE,  UNAVAILABLE_FOR_LEGAL_REASONS,  INTERNAL_SERVER_ERROR,  NOT_IMPLEMENTED,  BAD_GATEWAY,  SERVICE_UNAVAILABLE,  GATEWAY_TIMEOUT,  HTTP_VERSION_NOT_SUPPORTED,  VARIANT_ALSO_NEGOTIATES,  INSUFFICIENT_STORAGE,  LOOP_DETECTED,  BANDWIDTH_LIMIT_EXCEEDED,  NOT_EXTENDED,  NETWORK_AUTHENTICATION_REQUIRED, 
  };
  @SerializedName("status")
  private StatusEnum status = null;
  @SerializedName("view")
  private View view = null;
  @SerializedName("viewName")
  private String viewName = null;

  /**
   **/
  @ApiModelProperty(value = "")
  public Boolean getEmpty() {
    return empty;
  }
  public void setEmpty(Boolean empty) {
    this.empty = empty;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public Object getModel() {
    return model;
  }
  public void setModel(Object model) {
    this.model = model;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public Map<String, Object> getModelMap() {
    return modelMap;
  }
  public void setModelMap(Map<String, Object> modelMap) {
    this.modelMap = modelMap;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public Boolean getReference() {
    return reference;
  }
  public void setReference(Boolean reference) {
    this.reference = reference;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public StatusEnum getStatus() {
    return status;
  }
  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public View getView() {
    return view;
  }
  public void setView(View view) {
    this.view = view;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public String getViewName() {
    return viewName;
  }
  public void setViewName(String viewName) {
    this.viewName = viewName;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ModelAndView modelAndView = (ModelAndView) o;
    return (this.empty == null ? modelAndView.empty == null : this.empty.equals(modelAndView.empty)) &&
        (this.model == null ? modelAndView.model == null : this.model.equals(modelAndView.model)) &&
        (this.modelMap == null ? modelAndView.modelMap == null : this.modelMap.equals(modelAndView.modelMap)) &&
        (this.reference == null ? modelAndView.reference == null : this.reference.equals(modelAndView.reference)) &&
        (this.status == null ? modelAndView.status == null : this.status.equals(modelAndView.status)) &&
        (this.view == null ? modelAndView.view == null : this.view.equals(modelAndView.view)) &&
        (this.viewName == null ? modelAndView.viewName == null : this.viewName.equals(modelAndView.viewName));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (this.empty == null ? 0: this.empty.hashCode());
    result = 31 * result + (this.model == null ? 0: this.model.hashCode());
    result = 31 * result + (this.modelMap == null ? 0: this.modelMap.hashCode());
    result = 31 * result + (this.reference == null ? 0: this.reference.hashCode());
    result = 31 * result + (this.status == null ? 0: this.status.hashCode());
    result = 31 * result + (this.view == null ? 0: this.view.hashCode());
    result = 31 * result + (this.viewName == null ? 0: this.viewName.hashCode());
    return result;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class ModelAndView {\n");
    
    sb.append("  empty: ").append(empty).append("\n");
    sb.append("  model: ").append(model).append("\n");
    sb.append("  modelMap: ").append(modelMap).append("\n");
    sb.append("  reference: ").append(reference).append("\n");
    sb.append("  status: ").append(status).append("\n");
    sb.append("  view: ").append(view).append("\n");
    sb.append("  viewName: ").append(viewName).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
