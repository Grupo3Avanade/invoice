package com.avanade.invoice.services;

import com.avanade.invoice.entities.Card;
import com.avanade.invoice.entities.User;
import com.avanade.invoice.entities.enums.CardType;
import com.avanade.invoice.exceptions.DatabaseException;
import com.avanade.invoice.exceptions.ResourceNotFoundException;
import com.avanade.invoice.payloads.request.RequestCard;
import com.avanade.invoice.payloads.response.ResponseCard;
import com.avanade.invoice.repositories.CardRepository;
import org.springframework.stereotype.Service;
import com.avanade.invoice.services.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CardService {

    private static Random random;
    private final CardRepository repository;
    private final UserService userService;

    public CardService(CardRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    private static Random getRandom() {
        if (Objects.isNull(random)) {
            random = new Random();
        }
        return random;
    }

    public ResponseCard getOne(UUID id) {
        return findOrFailById(id).toResponse();
    }

    public List<ResponseCard> getAllByUser(UUID id) {
        return repository.findByUserId(id)
                .stream()
                .map(Card::toResponse).collect(Collectors.toList());
    }

    public Card findOrFailById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Card could not be found"));
    }

    public List<Card> findAllByClosingDay(Integer closingDay){
        return repository.findAllByClosingDay(closingDay);
    }

    public ResponseCard create(RequestCard requestCard) {

        String accountNumber = prepareRandomNumber(5);
        String securityCode = prepareRandomNumber(4);
        String cardNumber = prepareRandomNumber(14);
        CardType cardType = generateRandomCardType();
        LocalDate cardExpirationDate = LocalDate.now().plusYears(5);

        User user = getUser(requestCard);

        Card card = new Card();
        card.setUser(user);
        card.setHolderName(requestCard.holderName());
//        card.setExpiry(cardExpirationDate);
//        card.setSecurityCode(securityCode);
//        card.setBankAccountId(accountNumber);
        card.setNumber(cardNumber);
        card.setType(cardType);
//        card.setIsDependent(requestCard.isDependent());
        card.setClosingDay(requestCard.closingDay());

        saveOrFail(card);
        return card.toResponse();
    }

    private String prepareRandomNumber(int length) {
        final int size = length <= 0 ? 1 : length;
        IntStream stream = getRandom().ints(size, 0, 9);
        StringBuilder builder = new StringBuilder();
        stream.forEachOrdered(builder::append);
        return builder.toString();
    }

    private CardType generateRandomCardType() {
        CardType[] cardTypes = CardType.values();
        random = getRandom();
        int index = random.nextInt(cardTypes.length);
        return cardTypes[index];
    }

    public ResponseCard update(UUID id, RequestCard requestCard) {
        Card card = findOrFailById(id);
        User user = getUser(requestCard);

        card.setUser(user);
        card.setHolderName(requestCard.holderName());
//        card.setIsDependent(requestCard.isDependent());
//        card.setExpiry(requestCard.expirationDate());

        saveOrFail(card);

        return card.toResponse();
    }


    private User getUser(RequestCard cartaoRequest) {
        return userService.findOrFailById(cartaoRequest.userId());
    }

    private void saveOrFail(Card card) {
        try {
            repository.saveAndFlush(card);
        } catch (Exception e) {
            throw new DatabaseException("Error while saving card");
        }
    }

    public String delete(UUID id) {
        repository.deleteById(id);
        return "Cartão " + id + " deletado com sucesso";
    }
}
