package producer.model;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author m0v1
 * @date 2021年05月08日 10:24 下午
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder(toBuilder = true)
public class SimpleMessageDTO implements Serializable {
    private static final long serialVersionUID = -7078811956396221586L;
    private String id;
    private Date createTime;
    private BigDecimal money;
}
