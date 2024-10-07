package com.ssamba.petsi.schedule_service.domain.schedule.enums;

import java.util.ArrayList;
import java.util.List;

import com.ssamba.petsi.schedule_service.domain.schedule.dto.response.GetSchedulesDetailPerMonthResponseDto;
import com.ssamba.petsi.schedule_service.global.dto.PetCustomDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ScheduleSortingStatus {
	DEFAULT("기본") {
		@Override
		public List<GetSchedulesDetailPerMonthResponseDto<PetCustomDto>> setReturnList(List<GetSchedulesDetailPerMonthResponseDto<PetCustomDto>> upcomingList,
			List<GetSchedulesDetailPerMonthResponseDto<PetCustomDto>> endedScheduleList) {
			List<GetSchedulesDetailPerMonthResponseDto<PetCustomDto>> returnList = new ArrayList<>();
			returnList.addAll(upcomingList);
			returnList.addAll(endedScheduleList);
			return returnList;
		}
	},
	UPCOMING("예정") {
		@Override
		public List<GetSchedulesDetailPerMonthResponseDto<PetCustomDto>> setReturnList(List<GetSchedulesDetailPerMonthResponseDto<PetCustomDto>> upcomingList,
			List<GetSchedulesDetailPerMonthResponseDto<PetCustomDto>> endedScheduleList) {
			return upcomingList;
		}
	},
	ENDED("완료") {
		@Override
		public List<GetSchedulesDetailPerMonthResponseDto<PetCustomDto>> setReturnList(List<GetSchedulesDetailPerMonthResponseDto<PetCustomDto>> upcomingList,
			List<GetSchedulesDetailPerMonthResponseDto<PetCustomDto>> endedScheduleList) {
			return endedScheduleList;
		}
	};


	public static ScheduleSortingStatus fromValue(String status) {
		if (status == null) {
			return ScheduleSortingStatus.DEFAULT;
		}
		return ScheduleSortingStatus.valueOf(status);
	}

	private final String value;

	public abstract List<GetSchedulesDetailPerMonthResponseDto<PetCustomDto>> setReturnList(
		List<GetSchedulesDetailPerMonthResponseDto<PetCustomDto>> upcomingList, List<GetSchedulesDetailPerMonthResponseDto<PetCustomDto>> endedScheduleList);

}
