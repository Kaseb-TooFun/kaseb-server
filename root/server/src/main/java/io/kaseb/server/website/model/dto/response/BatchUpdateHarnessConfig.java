package io.kaseb.server.website.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchUpdateHarnessConfig {
	private int totalCount;
	private int successCount;
	private int failedCount;

}
