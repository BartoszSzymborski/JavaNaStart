package pl.javastart.equipy.components.assigment;

import jdk.nashorn.internal.ir.Assignment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.javastart.equipy.inventory.asset.Asset;
import pl.javastart.equipy.inventory.asset.AssetRepository;
import pl.javastart.equipy.inventory.user.User;
import pl.javastart.equipy.inventory.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AssignmentService {

    private AssigmentRepository assigmentRepository;
    private AssetRepository assetRepository;
    private UserRepository userRepository;

    public AssignmentService(AssigmentRepository assigmentRepository, AssetRepository assetRepository, UserRepository userRepository) {
        this.assigmentRepository = assigmentRepository;
        this.assetRepository = assetRepository;
        this.userRepository = userRepository;
    }

    AssignmentDto createAssigment(AssignmentDto assignmentDto){
        Optional<Assigment>activeAssignmentForAsset = assigmentRepository.findByAsset_IdAndEndIsNull(assignmentDto.getAssetId());
        activeAssignmentForAsset.ifPresent((a->{
            throw new InvalidAssignmentException("To wyposażenie jest aktualnei do kogoś przypisane");
        }));
        Optional<User>user = userRepository.findById(assignmentDto.getUserId());
        Optional<Asset>asset = assetRepository.findById(assignmentDto.getAssetId());
        Assigment assigment = new Assigment();
        Long userId = assignmentDto.getUserId();
        Long assetId = assignmentDto.getAssetId();
        assigment.setUser(user.orElseThrow(()->
                new InvalidAssignmentException("Brak użytkownika z id: " + userId)));
        assigment.setAsset(asset.orElseThrow(()->
                new InvalidAssignmentException("Brak wyposażenia o id" + assetId)));
        assigment.setStart(LocalDateTime.now());
        return AssignmentMapper.toDto(assigmentRepository.save(assigment));

    }

    @Transactional
    public LocalDateTime finishAssignment(Long assignmentId){
        Optional<Assigment>assigment = assigmentRepository.findById(assignmentId);
        Assigment assigmentEntity = assigment.orElseThrow(AssignmentNotFoundException::new);
        if (assigmentEntity.getEnd()!=null)
            throw new AssignmentAlreadyFinishedException();
        else
            assigmentEntity.setEnd(LocalDateTime.now());
        return assigmentEntity.getEnd();
    }


}
