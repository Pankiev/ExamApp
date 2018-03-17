package pl.exam.app.jsf.beans.user;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import pl.exam.app.database.entities.User;
import pl.exam.app.database.repositories.UserRepository;

import java.util.List;
import java.util.Map;

public class LazyUserDataModel extends LazyDataModel<User>
{
	private UserRepository userRepository;

	public LazyUserDataModel(UserRepository userRepository)
	{
		this.userRepository = userRepository;
	}

	@Override
	public User getRowData(String rowKey)
	{
		return userRepository.findById(Integer.valueOf(rowKey)).get();
	}

	@Override
	public Object getRowKey(User user)
	{
		return user.getId();
	}

	@Override
	public List<User> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters)
	{
		Sort sort = createSortSpec(sortField, sortOrder);
		PageRequest request = PageRequest.of(first / pageSize, pageSize, sort);
		Page<User> data = userRepository.findAll(request);
		this.setRowCount((int) data.getTotalElements());
		return data.getContent();
	}

	private Sort createSortSpec(String sortField, SortOrder sortOrder)
	{
		if(sortField == null)
			return new Sort(Sort.Direction.ASC, "schoolClass", "idInClass")
					.and(new Sort(Sort.Direction.DESC, "creationDate"));
		return new Sort(toSortDirction(sortOrder), sortField);
	}

	private Sort.Direction toSortDirction(SortOrder sortOrder)
	{
		return sortOrder.equals(SortOrder.DESCENDING) ? Sort.Direction.DESC : Sort.Direction.ASC;
	}
}
