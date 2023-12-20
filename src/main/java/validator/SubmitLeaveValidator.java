package validator;

import com.laps.backend.models.SubmitLeaveDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class SubmitLeaveValidator implements Validator {

    private List<LocalDate> publicHolidays;

    public SubmitLeaveValidator(List<LocalDate> publicHolidays) {
        this.publicHolidays = publicHolidays;
    }
    @Override
    public void validate(Object target, Errors errors) {
        SubmitLeaveDto submitLeaveDto=(SubmitLeaveDto) target;
        // Check if mandatory details are provided

        if (submitLeaveDto.getFromDate() == null || submitLeaveDto.getToDate() == null ||
                submitLeaveDto.getLeaveCategory() == null || submitLeaveDto.getAdditionalReasons() == null) {
            return;
        }

        if ((submitLeaveDto.getFromDate() != null && submitLeaveDto.getToDate() != null) &&
                (submitLeaveDto.getFromDate().compareTo(submitLeaveDto.getToDate()) > 0))
        {
            errors.reject("toDate", "End date should be greater than start date.");
            errors.rejectValue("toDate", "error.dates", "to date must be > from date");
        }
        // Check if dates are in chronologically increasing order
        if (submitLeaveDto.getFromDate().isAfter(submitLeaveDto.getToDate())) {
            return ;
        }

        // Check annual leave computation
        long leaveDays = submitLeaveDto.getFromDate().until(submitLeaveDto.getToDate().plusDays(1)).getDays();
        boolean includeWeekendsHolidays = leaveDays > 14;

        if (includeWeekendsHolidays) {
            // Additional logic for annual leave computation
            // ...

            // Check if From and To dates are working days
            if (!isWorkingDay(submitLeaveDto.getFromDate()) || !isWorkingDay(submitLeaveDto.getToDate())) {
                return ;
            }
        }

        // Additional validation logic based on employee designation
        // For example, you can check entitlement based on designation
        if (leaveDays > 60 && "Medical".equalsIgnoreCase(submitLeaveDto.getLeaveCategory())) {
            return ;
        }

        return ; // Valid if all conditions are met
    }


    private boolean isWorkingDay(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY && !publicHolidays.contains(date);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

}
