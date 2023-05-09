package com.api.movierental.converters;

import com.api.movierental.dtos.MovieDto;
import com.api.movierental.models.MovieModel;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieConverter {

    private final ModelMapper modelMapper;

    public MovieConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public MovieDto convertToDto(MovieModel movieModel) {
        return modelMapper.map(movieModel, MovieDto.class);
    }

    public List<MovieDto> convertToDtoList(List<MovieModel> movies) {
        return movies.stream()
                .map(this::convertToDto)
                .toList();
    }

    public Page<MovieDto> convertToDtoPage(Page<MovieModel> moviePageDto) {
        return moviePageDto.map(this::convertToDto);
    }

    public MovieModel mapToMovieModel(MovieDto movieDto) {
        return modelMapper.map(movieDto, MovieModel.class);
    }

    public MovieModel mapToMovieModel(MovieDto movieDto, MovieModel movieModel) {
        modelMapper.map(movieDto, movieModel);
        return movieModel;
    }
}
