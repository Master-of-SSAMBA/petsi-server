package com.ssamba.petsi.schedule_service.domain.schedule.enums;

import java.util.List;

import com.ssamba.petsi.schedule_service.domain.schedule.dto.response.GetSchedulesDetailPerMonthResponseDto;
import com.ssamba.petsi.schedule_service.global.dto.PetCustomDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ScheduleStatus {
	ACTIVATED("활성") {
		@Override
		public List<GetSchedulesDetailPerMonthResponseDto<PetCustomDto>> setReturnList(List<GetSchedulesDetailPerMonthResponseDto<PetCustomDto>> upcommingList,
			List<GetSchedulesDetailPerMonthResponseDto<PetCustomDto>> endedScheduleList) {
			return List.of();
		}
	},
	INACTIVATED("비활성") {
		@Override
		public List<GetSchedulesDetailPerMonthResponseDto<PetCustomDto>> setReturnList(List<GetSchedulesDetailPerMonthResponseDto<PetCustomDto>> upcommingList,
			List<GetSchedulesDetailPerMonthResponseDto<PetCustomDto>> endedScheduleList) {
			return List.of();
		}
	},
	ENDED("완료") {
		@Override
		public List<GetSchedulesDetailPerMonthResponseDto<PetCustomDto>> setReturnList(List<GetSchedulesDetailPerMonthResponseDto<PetCustomDto>> upcommingList,
			List<GetSchedulesDetailPerMonthResponseDto<PetCustomDto>> endedScheduleList) {
			return List.of();
		}
	};

	private final String value;


	public abstract List<GetSchedulesDetailPerMonthResponseDto<PetCustomDto>> setReturnList(
		List<GetSchedulesDetailPerMonthResponseDto<PetCustomDto>> upcommingList, List<GetSchedulesDetailPerMonthResponseDto<PetCustomDto>> endedScheduleList);
}

