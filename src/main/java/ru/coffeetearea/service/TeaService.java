package ru.coffeetearea.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.coffeetearea.dto.PageDTO;
import ru.coffeetearea.dto.SortingParams;
import ru.coffeetearea.dto.TeaDTO;
import ru.coffeetearea.exceptions.EntityNotFoundException;
import ru.coffeetearea.mappers.TeaMapper;
import ru.coffeetearea.model.Tea;
import ru.coffeetearea.repository.TeaRepository;
import ru.coffeetearea.specification.DrinksSpecification;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TeaService {

    // Fields

    private final TeaRepository teaRepository;

    private final TeaMapper teaMapper;

    private final DrinkService drinkService;


    /**
     * Редктирование напитка чая
     *
     * @param teaId
     * @param teaDTO
     * @return teaDTO
     */
    public TeaDTO editTea(Long teaId, TeaDTO teaDTO) {

        Tea tea = teaRepository
                .findById(teaId)
                .orElseThrow(() -> new EntityNotFoundException(teaId));

        tea.setName(teaDTO.getName());
        tea.setPrice(teaDTO.getPrice());
        tea.setAbout(teaDTO.getAbout());
        tea.setPackaging(teaMapper.teaDTOtoTea(teaDTO).getPackaging());
        tea.setManufacturer(teaMapper.teaDTOtoTea(teaDTO).getManufacturer());
        tea.setCountry(teaMapper.teaDTOtoTea(teaDTO).getCountry());
        tea.setWeight(teaDTO.getWeight());
        tea.setTeaType(teaMapper.teaDTOtoTea(teaDTO).getTeaType());
        tea.setTeaColor(teaMapper.teaDTOtoTea(teaDTO).getTeaColor());

        teaRepository.save(tea);

        return teaMapper.teaToTeaDTO(teaRepository.getOne(teaId));
    }


    /**
     * Удалить чай из списка товаров.
     * На самом деле меняем всего лишь поле is_deleted.
     */
    public void deleteTeaFromDrinks(Long teaId) {

        Tea tea = teaRepository.findById(teaId).orElseThrow(() -> new EntityNotFoundException(teaId));

        tea.setDeleted(true);

        teaRepository.save(tea);
    }


    /**
     * Добавить новый чай в список товаров
     *
     * @param teaDTO
     * @return
     */
    public TeaDTO addTea(TeaDTO teaDTO) {

        Tea tea = teaMapper.teaDTOtoTea(teaDTO);

        teaRepository.save(tea);

        return teaMapper.teaToTeaDTO(tea);
    }

    /**
     * Метод для вывода всего чая
     *
     * @param page
     * @param pageSize
     * @return CoffeesDTOs
     */
    public PageDTO<TeaDTO> findAll(int page, int pageSize, SortingParams sortingParams) {

        final Page<Tea> teas = teaRepository.findAll(drinkService.sortingWithParams(sortingParams, page, pageSize));

        return new PageDTO<>(teaMapper.teaToTeasDTO(teas));
    }


    /**
     * Метод для возвращение ЧАЯ через фильтрацию
     *
     * @param page
     * @param pageSize
     * @param colorId
     * @param typeId
     * @param countryId
     * @return filtered Coffees(DTOs)
     */
    public PageDTO<TeaDTO> findAllByFilter(int page, int pageSize, Long colorId, Long typeId, Long countryId,
                                           BigDecimal min, BigDecimal max, SortingParams sortingParams) {

        final Page<Tea> teas = teaRepository
                .findAll(DrinksSpecification
                                .getTeasByFilter(colorId, typeId, countryId, min, max),
                        drinkService.sortingWithParams(sortingParams, page, pageSize));

        return new PageDTO<>(teaMapper.teaToTeasDTO(teas));
    }
}